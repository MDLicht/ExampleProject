#include <jni.h>
#include <opencv2/opencv.hpp>

using namespace cv;

extern "C"
JNIEXPORT void JNICALL
Java_com_mdlicht_zb_exampleproject_opencv_activity_OpenCvActivity_ConvertRGBtoGray(JNIEnv *env,
                                                                                     jobject instance,
                                                                                     jlong matAddrInput,
                                                                                     jlong matAddrResult) {
// 입력 RGBA 이미지를 GRAY 이미지로 변환

    Mat &matInput = *(Mat *) matAddrInput;

    Mat &matResult = *(Mat *) matAddrResult;


    cvtColor(matInput, matResult, CV_RGBA2GRAY);
}