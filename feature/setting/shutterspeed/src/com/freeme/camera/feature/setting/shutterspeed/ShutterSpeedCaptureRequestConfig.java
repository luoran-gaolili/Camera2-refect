/*
 * Copyright Statement:
 *
 *   This software/firmware and related documentation ("MediaTek Software") are
 *   protected under relevant copyright laws. The information contained herein is
 *   confidential and proprietary to MediaTek Inc. and/or its licensors. Without
 *   the prior written permission of MediaTek inc. and/or its licensors, any
 *   reproduction, modification, use or disclosure of MediaTek Software, and
 *   information contained herein, in whole or in part, shall be strictly
 *   prohibited.
 *
 *   MediaTek Inc. (C) 2017. All rights reserved.
 *
 *   BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 *   THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 *   RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER
 *   ON AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL
 *   WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 *   WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 *   NONINFRINGEMENT. NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH
 *   RESPECT TO THE SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY,
 *   INCORPORATED IN, OR SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES
 *   TO LOOK ONLY TO SUCH THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO.
 *   RECEIVER EXPRESSLY ACKNOWLEDGES THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO
 *   OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES CONTAINED IN MEDIATEK
 *   SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK SOFTWARE
 *   RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 *   STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S
 *   ENTIRE AND CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE
 *   RELEASED HEREUNDER WILL BE, AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE
 *   MEDIATEK SOFTWARE AT ISSUE, OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE
 *   CHARGE PAID BY RECEIVER TO MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 *   The following software/firmware and/or related documentation ("MediaTek
 *   Software") have been modified by MediaTek Inc. All revisions are subject to
 *   any receiver's applicable license agreements with MediaTek Inc.
 */
package com.freeme.camera.feature.setting.shutterspeed;

import android.hardware.camera2.CameraCaptureSession;
import android.hardware.camera2.CameraCharacteristics;
import android.hardware.camera2.CameraMetadata;
import android.hardware.camera2.CaptureRequest;
import android.view.Surface;

import com.freeme.camera.common.debug.LogHelper;
import com.freeme.camera.common.debug.LogUtil;
import com.freeme.camera.common.device.v2.Camera2Proxy;
import com.freeme.camera.common.setting.ICameraSetting;
import com.freeme.camera.common.setting.ISettingManager;

import java.util.List;

/**
 * Configure shutter speed in capture request in camera api2.
 */

class ShutterSpeedCaptureRequestConfig implements ICameraSetting.ICaptureRequestConfigure {
    private static final LogUtil.Tag TAG = new LogUtil.Tag(ShutterSpeedCaptureRequestConfig.class
            .getSimpleName());

    private ShutterSpeed mShutterSpeed;
    private static final int S_TO_NS = 1000000000;

    /**
     * Shutter speed capture request configure constructor.
     *
     * @param shutterSpeed The instance of {@link ShutterSpeed}.
     */
    public ShutterSpeedCaptureRequestConfig(ShutterSpeed shutterSpeed, ISettingManager
            .SettingDevice2Requester device2Requester) {
        mShutterSpeed = shutterSpeed;
    }

    @Override
    public void setCameraCharacteristics(CameraCharacteristics characteristics) {
        mShutterSpeed.onValueInitialized(ShutterSpeedHelper.getSupportedList(characteristics),
                ShutterSpeedHelper.ONE_SECONDS);
    }

    @Override
    public void configCaptureRequest(CaptureRequest.Builder captureBuilder) {
        CaptureRequest request = captureBuilder.build();
        int requestTemplate = request.get(CaptureRequest.CONTROL_CAPTURE_INTENT);
        boolean isNormalCapture = Camera2Proxy.TEMPLATE_STILL_CAPTURE == requestTemplate;
        String value = mShutterSpeed.getValue();
        LogHelper.d(TAG, "[configCaptureRequest] value " + value);
        if (!isNormalCapture || ShutterSpeedHelper.AUTO.equals(value)) {
            captureBuilder.set(CaptureRequest.CONTROL_MODE,
                    CameraMetadata.CONTROL_MODE_AUTO);
            return;
        }
        captureBuilder.set(CaptureRequest.CONTROL_MODE,
                CameraMetadata.CONTROL_MODE_OFF);
        captureBuilder.set(CaptureRequest.SENSOR_EXPOSURE_TIME,
                Long.parseLong(value) * S_TO_NS);
    }

    @Override
    public void configSessionSurface(List<Surface> surfaces) {

    }

    @Override
    public CameraCaptureSession.CaptureCallback getRepeatingCaptureCallback() {
        return null;
    }

    @Override
    public void sendSettingChangeRequest() {

    }
}
