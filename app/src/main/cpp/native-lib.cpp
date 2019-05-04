#include <jni.h>
#include <opencv2/opencv.hpp>

using namespace cv;

extern "C"
JNIEXPORT void JNICALL
Java_com_mdlicht_zb_exampleproject_opencv_activity_OpenCvActivity_processImage(JNIEnv *env,
                                                                               jobject instance,
                                                                               jlong matAddrInput,
                                                                               jlong matAddrResult,
                                                                               jlong matAddrResultRoi) {
    // 입력 RGBA 이미지를 GRAY 이미지로 변환
    Mat &matInput = *(Mat *) matAddrInput;
    Mat &matResult = *(Mat *) matAddrResult;
    Mat &matResultRoi = *(Mat *) matAddrResultRoi;

    int sy = (int) (matInput.rows * 0.3);
    int sx = (int) (matInput.cols * 0.3);
    int height = (int) (matInput.rows * 0.4);
    int width = (int) (matInput.cols * 0.4);
    Rect rect = Rect(sx, sy, width, height);
    rectangle(matInput, rect, Scalar(255, 0, 0), 3);

    Mat roi = matInput(rect);
    cvtColor(roi, roi, CV_RGBA2GRAY);
    Mat bin = roi.clone();
    threshold(bin, bin, 127, 255, CV_THRESH_BINARY);
    erode(bin, bin, (3, 3));
    dilate(bin, bin, (3, 3));
    matResultRoi = bin;
    cvtColor(roi, matInput(rect), CV_GRAY2RGBA);

    matResult = matInput;
}