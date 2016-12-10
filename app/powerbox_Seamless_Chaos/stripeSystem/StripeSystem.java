package org.fleen.blanketFlower.app.powerbox_Seamless_Chaos.stripeSystem;

import java.awt.Color;

/* 
 * just stripes. rectangles.
 * a symmetric square structure of moving stripes
 * a window into this structure. 720p or 1080p or 4k or 100x200 or whatever
 * 
 * a stripe's dance goes like this, for example
 * the stripe starts at an arbitrary location
 * it moves southwards
 * it moves until it passes beyond the viewport, to the south edge of the square
 * when it hits the south edge it teleports to the opposite edge of the square, to the north edge
 * then it keeps moving.
 * we move the stripe a number of movement increments (1,2,4 or whatever) so that the sum of those increments is an
 *   integer multiple of the span of the square. Thus smooth looping is achieved.
 *   
 * we start with a desired viewport. A rectangle
 * we define the box so that stripes that appear and disappear do so outside of view
 * we create all of our stripes at init
 * 
 * ---
 * RESOLUTION
 * 
 * 4k = (3840 x 2160) : 3840/2160 = 1.778
 * 1080p (1920x1080) : 1920/1080= 1.778
 * 720p (1280x720) : 1280/720= 1.778
 * 
 * a good visible cell array rez is 128x128, with 4 pix cells, that's our old power box, which looks good
 * 
 * so our visible cell array (VCA) should be (trying to keep the height around 128)
 *   128x72?
 *   
 *   160x90?
 *   320x180?
 *   
 *   given 320x180
 *   at 720p the cells are 4px
 *   at 1080p : 6
 *   at 4k : 12
 *   
 *   given 160x90
 *   720p:8px
 *   1080p:12px
 *   4k:24px
 *   
 * 
 * ---
 * 
 * our background should never be exposed. It's sloppy. 
 * To keep it covered with a seamless row of stripes is probably the way to go
 * WE WILL HAVE 2 SETS OF STRIPES
 *   the base stripes, the seamless course, and the chaos stripes, thrown on at random
 * 
 * ---
 * 
 * max stripe thickness shall be the width of the visible cell array : 320
 * therefor the square span shall be VCA.width+(MAXSTRIPETHICKNESS*2)
 * SQUARESPAN = 960
 * 
 * ---
 * 
 * stripe speeds, small common factors of 320 and 180. 
 *   1,2,4,6.. higher? experiment with that.
 *   
 * ---
 * 
 * It works like this
 * we have a viewport polygon. A rectangle
 * we have a collection of stripe polygons, more rectangles
 * we render cells shared by viewport and stripes
 * we move stripes
 *  
 */
public class StripeSystem{
  
  /*
   * ################################
   * CONSTRUCTORS
   * ################################        
   */
  
  StripeSystem(int resolution,int complexity,int excitement,int colorcount){
    
  }
  
  StripeSystem(int resolution,int complexity,int excitement,Color[] palette){
    
  }
  
  /*
   * ################################
   * GEOMETRY
   * The resolution of this system depends on the dimensions of the base
   *   The base is the visible rectangle. A block of cells. The thing you look at.
   * We have 2 resolutions, high and low
   * ################################        
   */
  
  public static final int 
    REZ_HI=0,
    REZ_LO=1;
  
  //params for high resolution stripe system
  public static final int 
    BASEWIDTH_HI=320,
    BASEHEIGHT_HI=180;
  
  
  

}
