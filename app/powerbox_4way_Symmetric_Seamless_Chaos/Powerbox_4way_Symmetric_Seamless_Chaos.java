package org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.renderer.Renderer;
import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.renderer.Renderer_Production;
import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.stripeSystem.StripeSystem;
import org.fleen.blanketFlower.app.powerbox_4way_Symmetric_Seamless_Chaos.ui.UI;

public class Powerbox_4way_Symmetric_Seamless_Chaos{
  
  /*
   * ################################
   * STRIPE SYSTEM
   * ################################
   */
  
  public StripeSystem stripesystem;
  
  /*
   * ################################
   * PALETTE
   * we have 3 ways of doing the palette
   *   specify
   *   choose from a list of nice palettes at random
   *   generate a palette with the palette mother
   * ################################
   */
  
  private Color[] palette=null;
  
  public Color[] getPalette(){
    if(palette==null)
      palette=getRandomNicePalette();
    return palette;}
  
  public int getPaletteSize(){
    Color[] palette=getPalette();
    return palette.length;}
  
  public void setPalette(Color[] palette){
    this.palette=palette;}
  
  /*
   * ++++++++++++++++++++++++++++++++++++++++
   * get random palette from NicePalettes
   */
  
  Color[] getRandomNicePalette(){
    Random rnd=new Random();
    return NicePalettes.PALETTES[rnd.nextInt(NicePalettes.PALETTES.length)];}
  
  /*
   * ++++++++++++++++++++++++++++++++++++++++
   * create palette via palette mother
   */
  
  private static final int CREATEDPALETTESIZE=16;
  
  Color[] createPMPalette(){
    return createPalette(CREATEDPALETTESIZE);}
  
  //PALETTE MOTHER
  private Color[] createPalette(int palettesize){
    Set<Color> a=new HashSet<Color>();
    Color c;
    Random rnd=new Random();
    for(int i=0;i<palettesize;i++){
      c=new Color(64+rnd.nextInt(12)*16,64+rnd.nextInt(12)*16,64+rnd.nextInt(12)*16);
      a.add(c);}
    Color[] p=a.toArray(new Color[a.size()]);
    return p;}
  
  /*
   * ################################
   * UI
   * ################################
   */
  
  static final String TITLE="Powerbox 4way Seamless Symmetric Chaos";
  static final int UIWIDTH=1500,UIHEIGHT=1010;
  public UI ui;
  
  private void initUI(){
    EventQueue.invokeLater(new Runnable(){
      public void run(){
        try{
          ui=new UI(Powerbox_4way_Symmetric_Seamless_Chaos.this,UIWIDTH,UIHEIGHT);
          ui.setVisible(true);
          ui.setTitle(TITLE);
          ui.getContentPane().setBackground(Color.black);
         }catch(Exception e){
           e.printStackTrace();}}});}
  
  /*
   * ################################
   * RENDERER
   * ################################
   */
  
  public BufferedImage image;
  Renderer renderer;
  
  private void renderToUI(){
    Renderer renderer=getRenderer();
    image=renderer.render();
    if(ui!=null)
      ui.imagepanel.repaint();}
  
  private Renderer getRenderer(){
    if(renderer==null)
      renderer=new Renderer_Production(this);
    return renderer;}
  
  /*
   * ################################
   * EXPORT
   * ################################
   */
  
  static final int EXPORTCELLSPAN=4;//720p
  //TODO we will export to 3 dirs at the same time : 720p,1080p,4k
  
  static final String EXPORTDIR="/home/john/Desktop/bfexport";
  
  RasterExporter rasterexporter=new RasterExporter(new File(EXPORTDIR));
  
  void export(int index){
    System.out.println("export");
    renderer.setCellSpan(EXPORTCELLSPAN);
    BufferedImage exportimage=renderer.render();
    rasterexporter.export(exportimage,index);}
  
  /*
   * ################################
   * TEST
   * ################################
   */
  
  private static final int UICELLSPAN=2;
  
  public void test(){
    System.out.println("START");
    initUI();
    stripesystem=new StripeSystem(
      StripeSystem.REZ_HI,2,3,getPaletteSize());
    getRenderer().setCellSpan(UICELLSPAN);
    boolean finished=false;
    int frameindex=0,maxframeindex=stripesystem.getReferenceSquare().span;
    while(!finished){
      finished=stripesystem.move();
      renderToUI();
      frameindex++;
      System.out.println(frameindex+"/"+maxframeindex);}
    System.out.println("END");}
  
  /*
   * ################################
   * GENERATE COMPOSITION
   * ################################
   */
  
  public void generateComposition(StripeSystem stripesystem,Color[] palette,String exportpath,int exportmode){
    System.out.println("GENERATE COMPOSITION : START");
    initUI();
    this.stripesystem=stripesystem;
    boolean finished=false;
    int frameindex=0,maxframeindex=stripesystem.getReferenceSquare().span;
    while(!finished){
      finished=stripesystem.move();
      renderToUI();
      export(frameindex);
      //export720p//TODO -- create directories as necessary too
      //export1080p
      //export4k
      frameindex++;
      System.out.println(frameindex+"/"+maxframeindex);
    }
    System.out.println("GENERATE COMPOSITION : END");
    
  }
  
  /*
   * ################################
   * GENERATE VOLUME OF COMPOSITIONS
   * random palettes
   * all hirez
   * randomish excitement and complexity
   * each composition in it's own 
   * exportmode is constant
   * compositions are named thusly
   *   Powerbox_4way_Symmetric_Seamless_Chaos_V123C123
   *   
   * that's the volume index and composition index
   * 
   * we create directories within the exportpath as necessary
   * 
   * ################################
   */
  
  public void generateComposition(int volumeindex,String exportpath,int compositioncount,int exportmode){
    
  }
  
  /*
   * ################################
   * MAIN
   * ################################
   */
  
  public static final void main(String[] a){
    new Powerbox_4way_Symmetric_Seamless_Chaos().test();}

}
