package org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.renderer;

import java.awt.image.BufferedImage;

public interface Renderer{
  
  BufferedImage render();
  
  void setCellSpan(int cellspan);

}
