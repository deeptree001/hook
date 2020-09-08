package com.pool.thread;

import com.pool.thread.netty.HookNettyServer;
import org.junit.Test;

/**
 * Unit test for simple App.
 */
public class AppTest {

  /**
   * Rigorous Test :-)
   */
  @Test
  public void shouldAnswerWithTrue() {
    HookEmbedded hookEmbedded = HookEmbedded.instance();
    for (int i = 0; i < 7; i++) {
      hookEmbedded.generateFixedThreadPool(3);
    }
    HookNettyServer instance = new HookNettyServer(2333);
  }
}
