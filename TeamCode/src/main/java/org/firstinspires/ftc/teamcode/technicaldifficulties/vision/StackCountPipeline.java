package org.firstinspires.ftc.teamcode.technicaldifficulties.vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class StackCountPipeline extends OpenCvPipeline {

    // Colors
    private static final Scalar blue = new Scalar(0, 0, 255);
    private static final Scalar green = new Scalar(0, 255, 0);

    // Detection Region
    private static final Point topLeftAnchor = new Point(0, 0);
    private static final int width = 35;
    private static final int height = 25;
    private static final Point bottomRightAnchor = new Point(topLeftAnchor.x + width, topLeftAnchor.y + height);

    // Ring Thresholds
    private static final int oneRingThreshold = 135;
    private static final int fourRingThreshold = 150;

    private Mat region1_Cb;
    private Mat YCrCb = new Mat();
    private Mat Cb = new Mat();
    private int avg;

    public volatile RingCount ringCount = RingCount.FOUR;

    @Override
    public void init(Mat firstFrame) {
        inputToCb(firstFrame);
        region1_Cb = Cb.submat(new Rect(topLeftAnchor, bottomRightAnchor));
    }

    @Override
    public Mat processFrame(Mat input) {
        inputToCb(input);
        avg = (int) Core.mean(region1_Cb).val[0];
        Imgproc.rectangle(input, topLeftAnchor, bottomRightAnchor, blue, 2);
        if(avg > fourRingThreshold) ringCount = RingCount.FOUR;
        else if(avg > oneRingThreshold) ringCount = RingCount.ONE;
        else ringCount = RingCount.ZERO;
        Imgproc.rectangle(input, topLeftAnchor, bottomRightAnchor, green, -1);
        return input;
    }

    private void inputToCb(Mat input) {
        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);
        Core.extractChannel(YCrCb, Cb, 1);
    }

    public int getAnalysis() {
        return avg;
    }

}
