package com.epriest.game.guildfantasy;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.MotionEventCompat;
import android.view.KeyEvent;
import android.view.MotionEvent;

import com.epriest.game.CanvasGL.graphics.CanvasUtil;
import com.epriest.game.CanvasGL.graphics.GLUtil;
import com.epriest.game.CanvasGL.graphics.GLView;
import com.epriest.game.CanvasGL.util.Scene;
import com.epriest.game.guildfantasy.main.Game_Guild;
import com.epriest.game.guildfantasy.main.Game_Main;
import com.epriest.game.guildfantasy.main.Game_Event;
import com.epriest.game.guildfantasy.main.Game_Home;
import com.epriest.game.guildfantasy.main.Game_Party;
import com.epriest.game.guildfantasy.main.Game_Member;
import com.epriest.game.guildfantasy.main.Game_Quest;
import com.epriest.game.guildfantasy.main.Game_Recruit;
import com.epriest.game.guildfantasy.main.Game_Skill;
import com.epriest.game.guildfantasy.main.Game_Title;
import com.epriest.game.guildfantasy.main.Scene_Event;
import com.epriest.game.guildfantasy.main.Scene_Guild;
import com.epriest.game.guildfantasy.main.Scene_Home;
import com.epriest.game.guildfantasy.main.Scene_Main;
import com.epriest.game.guildfantasy.main.Scene_Member;
import com.epriest.game.guildfantasy.main.Scene_Quest;
import com.epriest.game.guildfantasy.main.Scene_Recruit;
import com.epriest.game.guildfantasy.main.Scene_Title;
import com.epriest.game.guildfantasy.main.Scene_Skill;
import com.epriest.game.guildfantasy.main.play.GameDbAdapter;
import com.epriest.game.guildfantasy.util.INN;

/**
 * Created by darka on 2016-10-25.
 */

public class MainGLView extends GLView {

    private Game_Main gameMain;
    private Game_Title gameTitle;
    private Game_Home gameHome;
    private Game_Member gameMember;
    private Game_Skill gameSkill;
    private Game_Recruit gameRecruit;
    private Game_Quest gameQuest;
    private Game_Event gameEvent;

    private Bitmap mCanvasBitmap;
    private Canvas mCanvas;
    private Scene mScene;
    private Scene_Main sceneMain;
    private Scene mTrashScene;

    private Bitmap pointer;

    private GameDbAdapter dbAdapter;

    public MainGLView(Context context) {
        super(context);
        appClass.gameState = INN.GAME_INTRO;
        appClass.isGameInit = true;
        appClass.isSceneInit = true;
        dbAdapter = new GameDbAdapter(context);
        dbAdapter.open();
    }

    @Override
    public String cOnSurfaceCreate() {
        return null;
    }

    @Override
    public void cOnSurfaceChanged(int width, int height) {
        if (mCanvasBitmap != null) {
            mCanvasBitmap.recycle();
            mCanvasBitmap = null;
        }
//        mCanvasBitmap = Bitmap.createBitmap(appClass.getTextureWidth(), appClass.getTextureHeight(), Bitmap.Config.RGB_565);
        mCanvasBitmap = Bitmap.createBitmap(appClass.getGameCanvasWidth(), appClass.getGameCanvasHeight(), Bitmap.Config.RGB_565);
        mCanvas = new Canvas(mCanvasBitmap);
        pointer = GLUtil.loadAssetsBitmap(appClass, "pointer.png", null);
//		mCanvas.translate(appClass.mScreenOverWidth, appClass.mScreenOverHeight);
    }

    @Override
    public void cUpdateLogic() {
        if (appClass.isGameInit) {
            gameTitle = null;
            gameHome = null;
            gameQuest = null;
            gameMember = null;
            gameSkill = null;
            gameRecruit = null;
            gameEvent = null;
            /*if (gameMain != null && appClass.gameState != INN.GAME_PARTY)
                gameMain.selectQuestEnty = null;*/
        }

        switch (appClass.gameState) {
            case INN.GAME_INTRO:
//                appClass.isGameInit = false;
                if (appClass.isGameInit) {
                    if (gameMain == null) {
                        gameMain = new Game_Main(appClass.getBaseContext(), dbAdapter);
                        gameMain.Init();
                    }

                    gameTitle = new Game_Title(gameMain);
                    gameTitle.Start();
                    appClass.isGameInit = false;
                }
                gameTitle.gUpdate();
                break;
/*            case Game_Main.GAME_MAIN:
                if (appClass.isGameInit) {
                    gameMain = new Game_Main(appClass.getBaseContext());
                    gameMain.Init();
                    gameMain.mainButtonAct(Game_Main.GAME_HOME);
                }
                break;*/
            case INN.GAME_HOME:
                if (appClass.isGameInit) {
                    gameHome = new Game_Home(gameMain);
                    gameHome.Start();
                    appClass.isGameInit = false;
                }
                gameHome.gUpdate();
                break;
            case INN.GAME_EVENT:
                if (appClass.isGameInit) {
                    gameEvent = new Game_Event(gameMain);
                    gameEvent.Start();
                    appClass.isGameInit = false;
                }
                gameEvent.gUpdate();
                break;
            case INN.GAME_MEMBER:
                if (appClass.isGameInit) {
                    gameMember = new Game_Member(gameMain);
                    gameMember.Start();
                    appClass.isGameInit = false;
                }
                gameMember.gUpdate();
                break;
            case INN.GAME_RECRUIT:
                if (appClass.isGameInit) {
                    gameRecruit = new Game_Recruit(gameMain);
                    gameRecruit.Start();
                    appClass.isGameInit = false;
                }
                gameRecruit.gUpdate();
                break;
            case INN.GAME_SKILL:
                if (appClass.isGameInit) {
                    gameSkill = new Game_Skill(gameMain);
                    gameSkill.Start();
                    appClass.isGameInit = false;
                }
                gameSkill.gUpdate();
                break;
            case INN.GAME_QUEST:
                if (appClass.isGameInit) {
                    gameQuest = new Game_Quest(gameMain);
                    gameQuest.Start();
                    appClass.isGameInit = false;
                }
                gameQuest.gUpdate();
                break;
        }
        // appClass.game.gameState();
    }

    @Override
    public Bitmap cOnDraw() {
        mCanvas.drawColor(Color.DKGRAY);
        if (appClass.isSceneInit) {
            if (mScene != null) {
                mScene.recycleScene();
                mScene = null;
//                System.gc();
            }
            if (appClass.gameState != INN.GAME_INTRO) {

            }
            switch (appClass.gameState) {
                case INN.GAME_INTRO:
                    mScene = new Scene_Title(gameTitle);
                    mScene.initScene(appClass);
                    appClass.isSceneInit = false;
                    break;
//                case Game_Main.GAME_MAIN:
/*                    sceneMain = new Scene_Main();
                    sceneMain.initScene(gameMain);*/
//                    break;
                case INN.GAME_HOME:
                    mScene = new Scene_Home(gameHome, sceneMain);
                    mScene.initScene(appClass);
                    appClass.isSceneInit = false;
                    break;
                case INN.GAME_EVENT:
                    mScene = new Scene_Event(gameEvent, sceneMain);
                    mScene.initScene(appClass);
                    appClass.isSceneInit = false;
                    break;
                case INN.GAME_MEMBER:
                    mScene = new Scene_Member(gameMember, sceneMain);
                    mScene.initScene(appClass);
                    appClass.isSceneInit = false;
                    break;
                case INN.GAME_RECRUIT:
                    mScene = new Scene_Recruit(gameRecruit, sceneMain);
                    mScene.initScene(appClass);
                    appClass.isSceneInit = false;
                    break;
                case INN.GAME_SKILL:
                    mScene = new Scene_Skill(gameSkill, sceneMain);
                    mScene.initScene(appClass);
                    appClass.isSceneInit = false;
                    break;
                case INN.GAME_QUEST:
                    mScene = new Scene_Quest(gameQuest, sceneMain);
                    mScene.initScene(appClass);
                    appClass.isSceneInit = false;
                    break;
            }
        }

        if (appClass.isGameInit || appClass.isSceneInit || mCanvas == null) {
            if (appClass.loadingBg == null) {
                appClass.loadingBg = GLUtil.loadAssetsBitmap(appClass, "loading.jpg", null);
            }
            mScene.drawLoading(mCanvas, appClass.loadingBg);
        } else {
            mScene.draw(mCanvas);
        }

//		drawGuideLine(mCanvas);
        drawFps(mCanvas);
//        drawTouchPoint(mCanvas);

        return mCanvasBitmap;
    }

    @Override
    public void cOnTouchEvent(MotionEvent event) {
        appClass.touch.action = MotionEventCompat.getActionMasked(event);
        switch (appClass.touch.action) {
            case MotionEvent.ACTION_DOWN: {
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final float x = MotionEventCompat.getX(event, pointerIndex) * appClass.mGameScaleValueWidth;
                final float y = MotionEventCompat.getY(event, pointerIndex) * appClass.mGameScaleValueHeight;
                appClass.touch.mDownX = x;
                appClass.touch.mDownY = y;
                appClass.touch.mLastTouchX = x;
                appClass.touch.mLastTouchY = y;
                appClass.touch.mActivePointerId = MotionEventCompat.getPointerId(event, 0);
                break;
            }

            case MotionEvent.ACTION_MOVE: {
                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final float x = MotionEventCompat.getX(event, pointerIndex) * appClass.mGameScaleValueWidth;
                final float y = MotionEventCompat.getY(event, pointerIndex) * appClass.mGameScaleValueHeight;

                // Calculate the distance moved
                final float dx = x - appClass.touch.mLastTouchX;
                final float dy = y - appClass.touch.mLastTouchY;

                appClass.touch.mPosX += dx;
                appClass.touch.mPosY -= dy;

                // Remember this touch position for the next move event
                appClass.touch.mLastTouchX = x;
                appClass.touch.mLastTouchY = y;

                break;
            }

            case MotionEvent.ACTION_UP: {
                appClass.touch.mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
            }

            case MotionEvent.ACTION_CANCEL: {
                appClass.touch.mActivePointerId = MotionEvent.INVALID_POINTER_ID;
                break;
            }

            /*case MotionEvent.ACTION_POINTER_UP: {

                final int pointerIndex = MotionEventCompat.getActionIndex(event);
                final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);

                if (pointerId == appClass.touch.mActivePointerId) {
                    // This was our active pointer going up. Choose a new
                    // active pointer and adjust accordingly.
                    final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                    appClass.touch.mLastTouchX = MotionEventCompat.getX(event, newPointerIndex) * appClass.mGameScaleValueWidth;
                    appClass.touch.mLastTouchY = MotionEventCompat.getY(event, newPointerIndex) * appClass.mGameScaleValueHeight;
                    appClass.touch.mActivePointerId = MotionEventCompat.getPointerId(event, newPointerIndex);
                }
                break;
            }*/
        }

        switch (appClass.gameState) {

            case INN.GAME_INTRO:
                gameTitle.gOnTouchEvent(event);
                break;
//            case Game_Main.GAME_MAIN:
//                appClass.gameState = Game_Main.GAME_HOME;
            case INN.GAME_HOME:
                gameHome.gOnTouchEvent(event);
                break;
            case INN.GAME_EVENT:
                gameEvent.gOnTouchEvent(event);
                break;
            case INN.GAME_MEMBER:
                gameMember.gOnTouchEvent(event);
                break;
            case INN.GAME_RECRUIT:
                gameRecruit.gOnTouchEvent(event);
                break;
            case INN.GAME_SKILL:
                gameSkill.gOnTouchEvent(event);
            case INN.GAME_QUEST:
                gameQuest.gOnTouchEvent(event);
                break;
        }
    }

    public void onBackPressed(MainActivity mainActivity) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(mainActivity);
        switch (appClass.gameState) {
            case INN.GAME_INTRO:
            case INN.GAME_HOME:
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
                appClass.gameState = INN.GAME_HOME;
                appClass.isGameInit = true;
                appClass.isSceneInit = true;
                break;
            case INN.GAME_MEMBER:
                appClass.gameState = INN.GAME_HOME;
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
            paint.setColor(Color.YELLOW);
            paint.setTextSize(20);
//			paint.setTextAlign(Align.RIGHT);
            CanvasUtil.drawBox(mCanvas, Color.argb(150, 0, 0, 0), true, 10, 0, 100, 40);
            mCanvas.drawText("fps-" + GLView.framelate, 10, 20, paint);
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
        /*drawPaint.setColor(Color.MAGENTA);
        drawPaint.setAlpha(150);
        mCanvas.drawRect(appClass.touch.mTouchX - 20, appClass.touch.mTouchY - 1,
                appClass.touch.mTouchX + 20, appClass.touch.mTouchY + 1, drawPaint);
        mCanvas.drawRect(appClass.touch.mTouchX - 1, appClass.touch.mTouchY - 20,
                appClass.touch.mTouchX + 1, appClass.touch.mTouchY + 20, drawPaint);
        // mCanvas.drawCircle(game.appClass.touch.axisX-10,
        // game.appClass.touch.axisY-10, 20, drawPaint);*/
        int value = pointer.getWidth() / 2;
        CanvasUtil.drawBitmap(pointer, mCanvas, (int) appClass.touch.mLastTouchX - value, (int) appClass.touch.mLastTouchY - value);

//        drawPaint.setColor(Color.YELLOW);
////		drawPaint.setAlpha(255);
//        drawPaint.setTextSize(20);
        String text = (int) appClass.touch.mLastTouchX + "," + (int) appClass.touch.mLastTouchY;
        CanvasUtil.drawString(mCanvas, text, 20, Color.YELLOW, Paint.Align.CENTER,
                (int) appClass.touch.mLastTouchX - value, (int) appClass.touch.mLastTouchY);
//        mCanvas.drawText((int) appClass.touch.mLastTouchY + "," + (int) appClass.touch.mLastTouchY,
//                0, appClass.getGameCanvasHeight(), drawPaint);
    }
}
