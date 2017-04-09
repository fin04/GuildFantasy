package com.epriest.game.guildfantasy;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.graphics.GLView;
import com.epriest.game.CanvasGL.util.ApplicationClass;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.CanvasGL.util.gameLog;
import com.epriest.game.guildfantasy.main.Game_Home;
import com.epriest.game.guildfantasy.main.Game_Main;
import com.epriest.game.guildfantasy.main.Game_Party;
import com.epriest.game.guildfantasy.main.Game_Member;
import com.epriest.game.guildfantasy.main.Game_Quest;
import com.epriest.game.guildfantasy.main.Game_Town;
import com.epriest.game.guildfantasy.main.Scene_Home;
import com.epriest.game.guildfantasy.main.Scene_Main;
import com.epriest.game.guildfantasy.main.Scene_Party;
import com.epriest.game.guildfantasy.main.Scene_Member;
import com.epriest.game.guildfantasy.main.Scene_Quest;
import com.epriest.game.guildfantasy.main.Scene_Title;
import com.epriest.game.guildfantasy.main.Scene_Town;

/**
 * Created by darka on 2016-10-25.
 */

public class MainGLView extends GLView {
    private Context mContext;
    private ApplicationClass appClass;

    private Game_Main gameMain;
    private Game_Home gameHome;
    private Game_Quest gameQuest;
    private Game_Member gameMember;
    private Game_Town gameTown;
    private Game_Party gameParty;

    private Bitmap mCanvasBitmap;
    private Canvas mCanvas;
    private Scene mScene;
    private Scene_Main sceneMain;
    private Scene mTrashScene;

    public MainGLView(Context context) {
        super(context);
        mContext = context;
        appClass = (ApplicationClass) context.getApplicationContext();
        appClass.gameFlag = Game_Main.GAME_INTRO;
        appClass.isGameInit = true;
        appClass.isSceneInit = true;
    }

    public Context cGLView() {
        return mContext;
    }

    @Override
    public String cOnSurfaceCreate() {
//        appClass.mGameOrientation = ApplicationClass.GAMECANVAS_ORIENTATION_LANDSCAPE;
        return appClass.getGameCanvasWidth() + "," + appClass.getGameCanvasHeight() + "," + appClass.mGameOrientation;
    }

    @Override
    public void cOnSurfaceChanged(int width, int height) {
        appClass.setScreenWidth(width);
        appClass.setScreenHeight(height);
        appClass.mGameScreenWidthVal = (float) appClass.getGameCanvasWidth() / (float) appClass.getScreenWidth();
        appClass.mGameScreenHeightVal = (float) appClass.getGameCanvasHeight() / (float) appClass.getScreenHeight();

//		gameLog.d("mGameScreenVal:" + appClass.getScreenWidth() + "/" + INN.GAMECANVAS_WIDTH + "="
//				+ appClass.mGameScreenVal);
//		gameLog.d("mScreenOverWidth:" + appClass.mScreenOverWidth + "\nmScreenOverHeight" + appClass.mScreenOverHeight);
//		gameLog.d("mGameCanvasWidth:" + appClass.mGameCanvasWidth + "\nmGameCanvasHeight" + appClass.mGameCanvasHeight);
//
        if (mCanvasBitmap != null) {
            mCanvasBitmap.recycle();
            mCanvasBitmap = null;
        }
        mCanvasBitmap = Bitmap.createBitmap(appClass.getGameCanvasWidth(), appClass.getGameCanvasHeight(), Bitmap.Config.RGB_565);
        mCanvas = new Canvas(mCanvasBitmap);
//		mCanvas.translate(appClass.mScreenOverWidth, appClass.mScreenOverHeight);
    }

    @Override
    public void cUpdateLogic() {
        if (appClass.isGameInit) {
            gameHome = null;
            gameQuest = null;
            gameMember = null;
            gameTown = null;
            gameParty = null;
        }
        switch (appClass.gameFlag) {
            case Game_Main.GAME_INTRO:
                appClass.isGameInit = false;
                break;
            case Game_Main.GAME_MAIN:
                if (appClass.isGameInit) {
                    gameMain = new Game_Main(mContext);
                    gameMain.Init();
                }
//                break;
            case Game_Main.GAME_HOME:
                if (appClass.isGameInit) {
                    gameHome = new Game_Home(gameMain);
                    gameHome.Start();
                    appClass.isGameInit = false;
                }
                gameHome.gUpdate();
                break;
            case Game_Main.GAME_QUEST:
                if (appClass.isGameInit) {
                    gameQuest = new Game_Quest(gameMain);
                    gameQuest.Start();
                    appClass.isGameInit = false;
                }
                gameQuest.gUpdate();
                break;
            case Game_Main.GAME_PARTY:
                if (appClass.isGameInit) {
                    gameParty = new Game_Party(gameMain);
                    gameParty.Start();
                    appClass.isGameInit = false;
                }
                gameParty.gUpdate();
                break;
            case Game_Main.GAME_MEMBER:
                if (appClass.isGameInit) {
                    gameMember = new Game_Member(gameMain);
                    gameMember.Start();
                    appClass.isGameInit = false;
                }
                gameMember.gUpdate();
                break;
            case Game_Main.GAME_TOWN:
                if (appClass.isGameInit) {
                    gameTown = new Game_Town(gameMain);
                    gameTown.Start();
                    appClass.isGameInit = false;
                }
                gameTown.gUpdate();
                break;
        }
        // appClass.game.gameState();
    }

    @Override
    public Bitmap cOnDraw() {
        mCanvas.drawColor(Color.BLACK);
        if (appClass.isSceneInit) {
            gameLog.d("flag : " + appClass.gameFlag);
            if (mScene != null) {
                mScene.recycleScene();
                mScene = null;
                System.gc();
            }
            switch (appClass.gameFlag) {
                case Game_Main.GAME_INTRO:
                    mScene = new Scene_Title();
                    mScene.initScene(appClass);
                    appClass.isSceneInit = false;
                    break;
                case Game_Main.GAME_MAIN:
                    sceneMain = new Scene_Main();
                    sceneMain.initScene(gameMain);
//                    break;
                case Game_Main.GAME_HOME:
                    mScene = new Scene_Home(gameHome, sceneMain);
                    mScene.initScene(appClass);
                    appClass.isSceneInit = false;
                    break;
                case Game_Main.GAME_QUEST:
                    mScene = new Scene_Quest(gameQuest, sceneMain);
                    mScene.initScene(appClass);
                    appClass.isSceneInit = false;
                    break;
                case Game_Main.GAME_PARTY:
                    mScene = new Scene_Party(gameParty, sceneMain);
                    mScene.initScene(appClass);
                    appClass.isSceneInit = false;
                    break;
                case Game_Main.GAME_MEMBER:
                    mScene = new Scene_Member(gameMember, sceneMain);
                    mScene.initScene(appClass);
                    appClass.isSceneInit = false;
                    break;
                case Game_Main.GAME_TOWN:
                    mScene = new Scene_Town(gameTown, sceneMain);
                    mScene.initScene(appClass);
                    appClass.isSceneInit = false;
                    break;
            }
        }

        if(appClass.isGameInit || appClass.isSceneInit){
            if(appClass.loadingBg == null){
                appClass.loadingBg = GLUtil.loadAssetsBitmap(appClass, "loading.jpg", null);
            }
            mScene.drawLoading(mCanvas, appClass.loadingBg);
        }else {
            mScene.draw(mCanvas);
        }

//		drawGuideLine(mCanvas);
        drawFps(mCanvas);
//        drawTouchPoint(mCanvas);

        return mCanvasBitmap;
    }

    @Override
    public void cOnTouchEvent(MotionEvent event) {
        appClass.touch.Action = event.getAction();
        appClass.touch.axisX = event.getX() * appClass.mGameScreenWidthVal;
        appClass.touch.axisY = event.getY() * appClass.mGameScreenHeightVal;
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            appClass.touch.oriX =  appClass.touch.axisX;
            appClass.touch.oriY = appClass.touch.axisY;
        }

        switch (appClass.gameFlag) {
            case Game_Main.GAME_INTRO:
                if (appClass.touch.Action != MotionEvent.ACTION_UP)
                    return;
                appClass.gameFlag = Game_Main.GAME_MAIN;
                appClass.isGameInit = true;
                appClass.isSceneInit = true;
                break;
            case Game_Main.GAME_MAIN:
                appClass.gameFlag = Game_Main.GAME_HOME;
            case Game_Main.GAME_HOME:
                gameHome.gOnTouchEvent(event);
                break;
            case Game_Main.GAME_QUEST:
                gameQuest.gOnTouchEvent(event);
                break;
            case Game_Main.GAME_PARTY:
                gameParty.gOnTouchEvent(event);
                break;
            case Game_Main.GAME_MEMBER:
                gameMember.gOnTouchEvent(event);
                break;
            case Game_Main.GAME_TOWN:
                gameTown.gOnTouchEvent(event);
                break;
        }
    }

    public void onBackPressed(MainActivity mainActivity) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        switch (appClass.gameFlag) {
            case Game_Main.GAME_INTRO:
            case Game_Main.GAME_HOME:
               /* builder.setTitle("알림")
                        .setMessage("게임을 종료하겠습니까?")
                        .setCancelable(false)
                        .setPositiveButton("확인", new android.content.DialogInterface.OnClickListener(){
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                android.os.Process.killProcess(android.os.Process.myPid());
                            }

                        }).setNegativeButton("취소", new android.content.DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();*/
                android.os.Process.killProcess(android.os.Process.myPid());
                break;
            default:
                appClass.gameFlag = Game_Main.GAME_HOME;
                appClass.isGameInit = true;
                appClass.isSceneInit = true;
                break;
            case Game_Main.GAME_PARTY:
                appClass.gameFlag = Game_Main.GAME_QUEST;
                appClass.isGameInit = true;
                appClass.isSceneInit = true;
                break;
        }
    }

    @Override
    public void cOnKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub

    }

    private void drawFps(Canvas mCanvas) {
        try {
            Paint paint = new Paint();
            paint.setColor(Color.RED);
            paint.setTextSize(20);
//			paint.setTextAlign(Align.RIGHT);
            mCanvas.drawText("fps: " + GLView.framelate,10,20, paint);
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    private void drawGuideLine(Canvas mCanvas) {
        Paint drawPaint = new Paint();
        // drawPaint.setColor(Color.RED);
        // mCanvas.drawRect(10, 10, mCanvas.getWidth()-10,
        // mCanvas.getHeight()-10, drawPaint);
        drawPaint.setColor(Color.BLUE);
        mCanvas.drawRect(mCanvas.getWidth() / 2, 0, mCanvas.getWidth() / 2 + 1, mCanvas.getHeight(), drawPaint);
        mCanvas.drawRect(mCanvas.getWidth() / 4, 0, mCanvas.getWidth() / 4 + 1, mCanvas.getHeight(), drawPaint);
        mCanvas.drawRect(mCanvas.getWidth() / 4 * 3, 0, mCanvas.getWidth() / 4 * 3 + 1, mCanvas.getHeight(), drawPaint);
        mCanvas.drawRect(0, mCanvas.getHeight() / 2, mCanvas.getWidth(), mCanvas.getHeight() / 2 + 1, drawPaint);
        mCanvas.drawRect(0, mCanvas.getHeight() / 4, mCanvas.getWidth(), mCanvas.getHeight() / 4 + 1, drawPaint);
        mCanvas.drawRect(0, mCanvas.getHeight() / 4 * 3, mCanvas.getWidth(), mCanvas.getHeight() / 4 * 3 + 1,
                drawPaint);
        drawPaint.setColor(Color.GREEN);
        drawPaint.setTextSize(20);
        mCanvas.drawText("" + mCanvas.getWidth() / 2, mCanvas.getWidth() / 2, 20, drawPaint);
        mCanvas.drawText("" + mCanvas.getWidth() / 4, mCanvas.getWidth() / 4, 20, drawPaint);
        mCanvas.drawText("" + mCanvas.getWidth() / 4 * 3, mCanvas.getWidth() / 4 * 3, 20, drawPaint);
        mCanvas.drawText("" + mCanvas.getHeight() / 2, mCanvas.getWidth() - 60, mCanvas.getHeight() / 2, drawPaint);
        mCanvas.drawText("" + mCanvas.getHeight() / 4, mCanvas.getWidth() - 60, mCanvas.getHeight() / 4, drawPaint);
        mCanvas.drawText("" + mCanvas.getHeight() / 4 * 3, mCanvas.getWidth() - 60, mCanvas.getHeight() / 4 * 3,
                drawPaint);
    }

    private void drawTouchPoint(Canvas mCanvas) {
        Paint drawPaint = new Paint();
        drawPaint.setColor(Color.MAGENTA);
        drawPaint.setAlpha(150);
        mCanvas.drawRect(appClass.touch.axisX - 20, appClass.touch.axisY - 1, appClass.touch.axisX + 20,
                appClass.touch.axisY + 1, drawPaint);
        mCanvas.drawRect(appClass.touch.axisX - 1, appClass.touch.axisY - 20, appClass.touch.axisX + 1,
                appClass.touch.axisY + 20, drawPaint);
        // mCanvas.drawCircle(game.appClass.touch.axisX-10,
        // game.appClass.touch.axisY-10, 20, drawPaint);
        drawPaint.setColor(Color.YELLOW);
//		drawPaint.setAlpha(255);
        drawPaint.setTextSize(20);
        mCanvas.drawText((int) appClass.touch.oriX + "," + (int) appClass.touch.oriY + "||" + (int) appClass.touch.axisX
                + "," + (int) appClass.touch.axisY, 0, appClass.getGameCanvasHeight(), drawPaint);
    }
}
