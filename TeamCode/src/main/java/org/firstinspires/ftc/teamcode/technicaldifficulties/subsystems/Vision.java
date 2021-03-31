package org.firstinspires.ftc.teamcode.technicaldifficulties.subsystems;

import com.disnodeteam.dogecommander.Subsystem;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.technicaldifficulties.vision.RingCount;
import org.firstinspires.ftc.teamcode.technicaldifficulties.vision.StackCountPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

public class Vision implements Subsystem {

    private HardwareMap hardwareMap;
    private Telemetry telemetry;

    private OpenCvInternalCamera phoneCam;
    private StackCountPipeline pipeline;

    public Vision(HardwareMap hardwareMap, Telemetry telemetry) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
    }

    @Override
    public void initHardware() {
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        pipeline = new StackCountPipeline();
        phoneCam.setPipeline(pipeline);

        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
        phoneCam.openCameraDeviceAsync(() -> phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPSIDE_DOWN));
    }

    @Override
    public void periodic() {
        telemetry.addData("Count", pipeline.ringCount);
        telemetry.addData("Analysis", pipeline.getAnalysis());
        telemetry.update();
    }

    public RingCount getRingCount() {
        return pipeline.ringCount;
    }
}
