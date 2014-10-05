#include "stdafx.h" 

#include<stdio.h>
#include <opencv/cv.h>
#include <opencv/highgui.h>
#include <ctype.h>
#define MAXCAM 6
int
main (int argc, char **argv)
{
	CvCapture *capture[MAXCAM];
    CvCapture *precapture = 0;
	IplImage *frame = 0;
	double w = 640, h = 480;
	int c, i, cn=0;
	char *filename=NULL;
    int icam=0;
    int mode=0;
	
	// (1)コマンド引数によって指定された番号のカメラに対するキャプチャ構造体を作成する
	for(i=1;i<argc;i++){
		if(strcmp(argv[i],"-c")==0 ) 	cn = argv[i+1][0]-'0';
		if(strcmp(argv[i],"-f")==0 ) 	filename=argv[i+1];
		if(strcmp(argv[i],"-n")==0 ) 	mode=1;
        if(strcmp(argv[i],"-w")==0 ) 	w=atoi(argv[i+1]);
		if(strcmp(argv[i],"-h")==0 ) 	h=atoi(argv[i+1]);
	}
    
    // -n 引数で、USBに接続されているカメラの数を返す
    if(mode==1){
        for(i=0;i<MAXCAM;i++){
            //printf("nCam = %d¥n",i);
            if( (capture[i]=cvCreateCameraCapture(i)) != 0 ){
                cvSetCaptureProperty (capture[i], CV_CAP_PROP_FRAME_WIDTH, w);
                cvSetCaptureProperty (capture[i], CV_CAP_PROP_FRAME_HEIGHT, h);

                if(capture[i]==precapture){
               //     cvReleaseCapture (&capture[i]);
                    break;
                }
             precapture=capture[i];
             //cvReleaseCapture (&capture[i]);
             icam++;
            } else break;
        }// next i
		for(i=0;i<icam;i++) cvReleaseCapture (&capture[i]);
        printf("nCam = %d¥n",icam);
        fflush(stdout);
        goto prg_end;
    }// mode
    
	//printf("cn = %d¥n",cn);
	//printf("filename = %s¥n",filename);
	for(i=0;i<=cn;i++){
	if((capture[i] = cvCreateCameraCapture(cn)) == 0 ){
        printf("error cn=%d¥n",cn);
        goto prg_end;
		}
	}
	
	/* この設定は，利用するカメラに依存する */
	// (2)キャプチャサイズを設定する．
	cvSetCaptureProperty (capture[cn], CV_CAP_PROP_FRAME_WIDTH, w);
	cvSetCaptureProperty (capture[cn], CV_CAP_PROP_FRAME_HEIGHT, h);
	
	if(filename == NULL ) cvNamedWindow ("Capture", CV_WINDOW_AUTOSIZE);
	
	// (3)カメラから画像をキャプチャする
	i=0;
	while(1){
		frame = cvQueryFrame (capture[cn]);
        if(filename!=NULL ){
			if(i>100){
            //	cvSaveImage( filename, frame );
            // After OpenCV 2.4
            cvSaveImage( filename, frame, 0 );
            cvReleaseCapture (&capture[cn]);
            break;
			}
			i++;
        }
        
		cvShowImage ("Capture", frame);
		c = cvWaitKey (2);
		if (c>0){
            cvReleaseCapture (&capture[cn]);
            cvDestroyWindow ("Capture");
			goto prg_end;
        }
	}
	
    //
	
prg_end:;
	return 0;
}

