/*
 *   Copyright Statement:
 *
 *     This software/firmware and related documentation ("MediaTek Software") are
 *     protected under relevant copyright laws. The information contained herein is
 *     confidential and proprietary to MediaTek Inc. and/or its licensor. Without
 *     the prior written permission of MediaTek inc. and/or its licensor, any
 *     reproduction, modification, use or disclosure of MediaTek Software, and
 *     information contained herein, in whole or in part, shall be strictly
 *     prohibited.
 *
 *     MediaTek Inc. (C) 2016. All rights reserved.
 *
 *     BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 *    THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 *     RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER
 *     ON AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL
 *     WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 *     WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 *     NON-INFRINGEMENT. NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH
 *     RESPECT TO THE SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY,
 *     INCORPORATED IN, OR SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES
 *     TO LOOK ONLY TO SUCH THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO.
 *     RECEIVER EXPRESSLY ACKNOWLEDGES THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO
 *     OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES CONTAINED IN MEDIATEK
 *     SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK SOFTWARE
 *     RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 *     STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S
 *     ENTIRE AND CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE
 *     RELEASED HEREUNDER WILL BE, AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE
 *     MEDIATEK SOFTWARE AT ISSUE, OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE
 *     CHARGE PAID BY RECEIVER TO MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 *     The following software/firmware and/or related documentation ("MediaTek
 *     Software") have been modified by MediaTek Inc. All revisions are subject to
 *     any receiver's applicable license agreements with MediaTek Inc.
 */

package com.freeme.camera.common.exif;

import com.freeme.camera.common.debug.LogHelper;
import com.freeme.camera.common.debug.LogHelper;
import com.freeme.camera.common.debug.LogUtil.Tag;

import java.io.IOException;

/**
 * Exif tool class.
 */
public class Exif {
    private static final Tag TAG = new Tag(Exif.class.getSimpleName());

    /**
     * Create Exif interface by jpeg data.
     * @param jpegData jpeg buffer.
     * @return an instance of ExifInterface.
     */
    public static ExifInterface getExif(byte[] jpegData) {
        ExifInterface exif = new ExifInterface();
        try {
            exif.readExif(jpegData);
        } catch (IOException e) {
            LogHelper.w(TAG, "Failed to read EXIF data", e);
        }
        return exif;
    }

    /**
     * Returns the degrees in clockwise. Values are 0, 90, 180, or 270.
     * @param exif exif interface.
     * @return the degree in jpeg.
     */
    public static int getOrientation(ExifInterface exif) {
        Integer val = exif.getTagIntValue(ExifInterface.TAG_ORIENTATION);
        if (val == null) {
            return 0;
        } else {
            return ExifInterface.getRotationForOrientationValue(val.shortValue());
        }
    }

    /**
     * Get jpeg orientation.
     * @param jpegData the jpeg data.
     * @return the orientation.
     */
    public static int getOrientation(byte[] jpegData) {
        if (jpegData == null) {
            return 0;
        }
        ExifInterface exif = getExif(jpegData);
        return getOrientation(exif);
    }
}