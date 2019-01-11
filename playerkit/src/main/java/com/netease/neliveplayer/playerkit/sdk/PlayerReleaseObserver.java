package com.netease.neliveplayer.playerkit.sdk;

/**
 * 播放器资源释放结束通知观察者
 * <p>
 * @author netease
 */
public interface PlayerReleaseObserver {
    /**
     * 接收到播放器资源释放结束通知
     */
    void onReceiver();
}
