package com.yichuizi.audiolibrary;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaCodec;
import android.media.MediaCodecInfo;
import android.media.MediaFormat;
import android.media.MediaRecorder;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.widget.Button;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * 作者：段益康 on 2019/1/1 18:48
 * 描述：
 */
public class AudioRecordService extends Service {
    private final int sampleRateInHz = 32000;
    private final int channelConfig = 1;
    private final int audioFormat = AudioFormat.ENCODING_PCM_16BIT;

    private class AudioDate {
        private ByteBuffer buffer;
        private int size;
    }

    private AudioRecorder mAudioRecorder;
    private AudioEncorder mAudioEncorder;

    private ArrayBlockingQueue queue;

    private String path;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new IAudioRecordeAidlInterface.Stub() {
            @Override
            public void startAudioRecord() throws RemoteException {
                startRecord();
            }

            @Override
            public String stopAudioRecord() throws RemoteException {
                stopRecord();
                return path;
            }
        };
    }


    private void startRecord() {
        queue = new ArrayBlockingQueue<>(1024);
        mAudioRecorder = new AudioRecorder();
        mAudioEncorder = new AudioEncorder();
        mAudioRecorder.start();
        mAudioEncorder.start();
    }

    private void stopRecord() {
        mAudioRecorder.stopRecording();
        mAudioEncorder.stopEncording();
    }

    /**
     * 录音线程
     */
    public class AudioRecorder extends Thread {

        private AudioRecord mAudioRecord;
        private boolean isRecording;
        private int minBufferSize;

        public AudioRecorder() {
            isRecording = true;
            initRecorder();
        }

        @Override
        public void run() {
            super.run();
            startRecording();
        }

        /**
         * 初始化录音
         */
        public void initRecorder() {
            minBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
            mAudioRecord = new AudioRecord(MediaRecorder.AudioSource.DEFAULT, sampleRateInHz, channelConfig, audioFormat, minBufferSize);
            if (mAudioRecord.getState() != AudioRecord.STATE_INITIALIZED) {
                isRecording = false;
                return;
            }
        }

        /**
         * 释放资源
         */
        public void release() {
            if (mAudioRecord != null && mAudioRecord.getState() == AudioRecord.STATE_INITIALIZED) {
                mAudioRecord.stop();
            }
        }

        /**
         * 开始录音
         */
        public void startRecording() {
            if (mAudioRecord == null) {
                return;
            }

            mAudioRecord.startRecording();
            while (isRecording) {
                AudioDate audioDate = new AudioDate();
                audioDate.buffer = ByteBuffer.allocateDirect(minBufferSize);
                audioDate.size = mAudioRecord.read(audioDate.buffer, minBufferSize);
                try {
                    if (queue != null) {
                        queue.put(audioDate);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            release();
        }

        /**
         * 结束录音
         */
        public void stopRecording() {
            isRecording = false;
        }
    }

    /**
     * 音频编码线程
     */
    public class AudioEncorder extends Thread {

        private MediaCodec mEncorder;
        private Boolean isEncording = false;
        private int minBufferSize;

        private OutputStream mFileStream;

        public AudioEncorder() {
            isEncording = true;
            initEncorder();
        }

        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void run() {
            super.run();
            startEncording();
        }

        /**
         * 初始化编码器
         */
        private void initEncorder() {
            minBufferSize = AudioRecord.getMinBufferSize(sampleRateInHz, channelConfig, audioFormat);
            try {
                mEncorder = MediaCodec.createEncoderByType(MediaFormat.MIMETYPE_AUDIO_AAC);
            } catch (IOException e) {
                e.printStackTrace();
            }
            MediaFormat format = MediaFormat.createAudioFormat(MediaFormat.MIMETYPE_AUDIO_AAC, sampleRateInHz, channelConfig);
            format.setString(MediaFormat.KEY_MIME, MediaFormat.MIMETYPE_AUDIO_AAC);
            format.setInteger(MediaFormat.KEY_AAC_PROFILE, MediaCodecInfo.CodecProfileLevel.AACObjectLC);
            format.setInteger(MediaFormat.KEY_BIT_RATE, 64000);
            format.setInteger(MediaFormat.KEY_MAX_INPUT_SIZE, minBufferSize * 2);

            mEncorder.configure(format, null, null, MediaCodec.CONFIGURE_FLAG_ENCODE);
        }

        /**
         * 开始编码
         */
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        public void startEncording() {
            if (mEncorder == null) {
                return;
            }

            mEncorder.start();
            try {
                path = getSDPath() + "/aac_encode.aac";
                mFileStream = new FileOutputStream(path);
                MediaCodec.BufferInfo mBufferInfo = new MediaCodec.BufferInfo();
                AudioDate audioDate;
                while (isEncording) {
                    // 从队列中取出录音的一帧音频数据
                    audioDate = getAudioDate();
                    System.out.println("编码中");
                    if (audioDate == null) {
                        continue;
                    }

                    // 取出 InputBuffer，填充音频数据，然后输送到编码器进行编码
                    int inputBufferIndex = mEncorder.dequeueInputBuffer(0);
                    if (inputBufferIndex >= 0) {
                        ByteBuffer inputBuffer = mEncorder.getInputBuffer(inputBufferIndex);
                        inputBuffer.clear();
                        inputBuffer.put(audioDate.buffer);
                        mEncorder.queueInputBuffer(inputBufferIndex, 0, audioDate.size, System.nanoTime(), 0);
                    }

                    // 取出编码好的一帧音频数据，然后给这一帧添加 ADTS 头
                    int outputBufferIndex = mEncorder.dequeueOutputBuffer(mBufferInfo, 0);
                    while (outputBufferIndex >= 0) {
                        int outBitsSize = mBufferInfo.size;
                        int outPacketSize = outBitsSize + 7; // ADTS 头部是 7 个字节
                        ByteBuffer outputBuffer = mEncorder.getOutputBuffer(outputBufferIndex);
                        outputBuffer.position(mBufferInfo.offset);
                        outputBuffer.limit(mBufferInfo.offset + outBitsSize);

                        byte[] outData = new byte[outPacketSize];
                        addADTStoPacket(outData, outPacketSize);

                        outputBuffer.get(outData, 7, outBitsSize);
                        outputBuffer.position(mBufferInfo.offset);
                        mFileStream.write(outData);
                        mEncorder.releaseOutputBuffer(outputBufferIndex, false);
                        outputBufferIndex = mEncorder.dequeueOutputBuffer(mBufferInfo, 0);
                    }
                }
                release();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * 停止编码
         */
        public void stopEncording() {
            isEncording = false;
        }

        /**
         * 从队列中取出一帧待编码的音频数据
         *
         * @return
         */
        public AudioDate getAudioDate() {
            if (queue != null) {
                try {
                    return (AudioDate) queue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        /**
         * 添加 ADTS 头
         *
         * @param packet
         * @param packetLen
         */
        private void addADTStoPacket(byte[] packet, int packetLen) {
            int profile = 2; //AAC LC
            int freqIdx = 4; //44100 根据不同的采样率修改这个值
            int chanCfg = 2; //CPE
            packet[0] = (byte) 0xFF;
            packet[1] = (byte) 0xF9;
            packet[2] = (byte) (((profile - 1) << 6) + (freqIdx << 2) + (chanCfg >> 2));
            packet[3] = (byte) (((chanCfg & 3) << 6) + (packetLen >> 11));
            packet[4] = (byte) ((packetLen & 0x7FF) >> 3);
            packet[5] = (byte) (((packetLen & 7) << 5) + 0x1F);
            packet[6] = (byte) 0xFC;
        }

        /**
         * 释放资源
         */
        public void release() {
            if (mFileStream != null) {
                try {
                    mFileStream.flush();
                    mFileStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (mEncorder != null) {
                mEncorder.stop();
            }
        }
    }

    public String getSDPath() {
        // 判断是否挂载
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
        return Environment.getRootDirectory().getAbsolutePath();
    }
}

