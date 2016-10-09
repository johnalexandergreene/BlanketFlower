package org.fleen.squarzy;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.fleen.squarzy.gSquid.SCell;
import org.fleen.squarzy.gSquid.SGrid;

public class Renderer_Production{
  
  public Renderer_Production(){
    colors=createPalette(COLORCOUNT);}
  
  public BufferedImage render(SGrid grid,int cellspan){
    int 
      gridwidth=grid.getWidth(),
      gridheight=grid.getHeight(),
      imagewidth=grid.getWidth()*cellspan,
      imageheight=grid.getHeight()*cellspan;
    BufferedImage image=new BufferedImage(imagewidth,imageheight,BufferedImage.TYPE_INT_RGB);
    //
    Color cellcolor;
    SCell cell;
    for(int x=0;x<gridwidth;x++){
      for(int y=0;y<gridheight;y++){
          cell=grid.getCell(x,y);
          cellcolor=colors[cell.test%colors.length];
          renderCell(image,cellcolor,x,y,cellspan);}}
  return image;}
  
  private void renderCell(BufferedImage image,Color color,int x,int y,int cellspan){
    int px,py,xoff=x*cellspan,yoff=y*cellspan;
    for(int cx=0;cx<cellspan;cx++){
      for(int cy=0;cy<cellspan;cy++){
        px=xoff+cx;
        py=yoff+cy;
        image.setRGB(px,py,color.getRGB());}}}
  
  /*
   * ################################
   * COLORS
   * ################################
   */
  
  private static final int COLORCOUNT=16;
  Color[] colors;
  
  //PALETTE MOTHER
  Color[] createPalette(int colorcount){
    Set<Color> a=new HashSet<Color>();
    Color c;
    Random rnd=new Random();
    for(int i=0;i<colorcount;i++){
      c=new Color(64+rnd.nextInt(12)*16,64+rnd.nextInt(12)*16,64+rnd.nextInt(12)*16);
      a.add(c);}
    return a.toArray(new Color[a.size()]);}

}
