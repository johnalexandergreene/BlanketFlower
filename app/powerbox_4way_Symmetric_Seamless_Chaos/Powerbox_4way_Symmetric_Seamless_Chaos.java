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
  private static final int UICELLSPAN=3;
  public UI ui;
  public BufferedImage uiimage;
  
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
  
  private void renderToUI(){
    Renderer renderer=getRenderer();
    uiimage=renderer.render(UICELLSPAN);
    if(ui!=null)
      ui.imagepanel.repaint();}
  
  /*
   * ################################
   * RENDERER
   * ################################
   */
  
  Renderer renderer;
  
  private Renderer getRenderer(){
    if(renderer==null)
      renderer=new Renderer_Production(this);
    return renderer;}
  
  /*
   * ################################
   * EXPORT
   * we do not handle dir creation here
   * we have 3 export modes : 720p, 1080p and 4k 
   * ################################
   */
  
  private RasterExporter rasterexporter=new RasterExporter();
  
  //EXPORT MODES
  private static final int 
    EXPORTMODE_720P=0,
    EXPORTMODE_1080P=1,
    EXPORTMODE_4k=2;
  
  //CELL SPAN BY REZ AND EXPORT MODE
  private static final int 
    CELLSPAN_HI_720P=4,
    CELLSPAN_HI_1080P=6,
    CELLSPAN_HI_4K=12,
    CELLSPAN_LO_720P=8,
    CELLSPAN_LO_1080P=12,
    CELLSPAN_LO_4K=24;
    
  void export(int exportmode,File exportdir,int frameindex){
    System.out.println("export "+getExportModeString(exportmode));
    int exportcellspan=getExportCellSpan(exportmode);
    BufferedImage exportimage=renderer.render(exportcellspan);
    rasterexporter.export(exportimage,frameindex,exportdir);}
  
  private String getExportModeString(int exportmode){
    switch(exportmode){
    case EXPORTMODE_720P:return "720P";
    case EXPORTMODE_1080P:return "1080P";
    case EXPORTMODE_4k:return "4K";
    default:throw new IllegalArgumentException("invalid export mode specified");}}
  
  private int getExportCellSpan(int exportmode){
    int rez=stripesystem.resolution;
    if(rez==StripeSystem.REZ_HI){
      switch(exportmode){
      case EXPORTMODE_720P:return CELLSPAN_HI_720P;
      case EXPORTMODE_1080P:return CELLSPAN_HI_1080P;
      case EXPORTMODE_4k:return CELLSPAN_HI_4K;
      default:throw new IllegalArgumentException("invalid export mode specified");}
    }else if(rez==StripeSystem.REZ_LO){
      switch(exportmode){
      case EXPORTMODE_720P:return CELLSPAN_LO_720P;
      case EXPORTMODE_1080P:return CELLSPAN_LO_1080P;
      case EXPORTMODE_4k:return CELLSPAN_LO_4K;
      default:throw new IllegalArgumentException("invalid export mode specified");}
    }else{
      throw new IllegalArgumentException("invalid rez");}}
  
  /*
   * ################################
   * TEST
   * ################################
   */
  
  public void test(){
    System.out.println("START");
    initUI();
    stripesystem=new StripeSystem(
      StripeSystem.REZ_HI,2,3,getPaletteSize());
    boolean finished=false;
    int frameindex=0,maxframeindex=stripesystem.getReferenceSquare().span;
    while(!finished){
      finished=stripesystem.move();
      renderToUI();
      frameindex++;
      System.out.println("FRAMEINDEX : "+frameindex+"/"+maxframeindex);}
    System.out.println("END");}
  
  /*
   * ################################
   * GENERATE COMPOSITION
   * create a dir. name=exportpath/compositionname
   * export png images to that dir
   * ################################
   */
  
  public void generateComposition(StripeSystem stripesystem,Color[] palette,int exportmode,String exportpath,String compositionname){
    System.out.println("GENERATE COMPOSITION ["+compositionname+"] START");
    //
    initUI();
    //create export dir
    File exportdir=new File(exportpath+"/"+compositionname);
    if(!exportdir.mkdir())throw new IllegalArgumentException("EXPORT DIR CREATION FAILED IN generateComposition");
    //
    this.stripesystem=stripesystem;
    boolean finished=false;
    int frameindex=0,maxframeindex=stripesystem.getMaxFrameIndex();
    while(!finished){
      finished=stripesystem.move();
      renderToUI();
      export(exportmode,exportdir,frameindex);
      frameindex++;
      System.out.println("FRAMEINDEX : "+frameindex+"/"+maxframeindex);}
    System.out.println("GENERATE COMPOSITION ["+compositionname+"] END");}
  
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
  
  static final String EXPORTPATH="/home/john/Desktop/bfexport";
  static final String SAMPLECOMPOSITIONNAME="Powerbox_4way_Symmetric_Seamless_Chaos_V123C456";
  
  public static final void main(String[] a){
    
    
    Powerbox_4way_Symmetric_Seamless_Chaos pb=new Powerbox_4way_Symmetric_Seamless_Chaos();
//    pb.test();
    Color[] palette=pb.getRandomNicePalette();
    StripeSystem stripesystem=new StripeSystem(StripeSystem.REZ_HI,2,3,palette.length);
    pb.generateComposition(stripesystem,palette,EXPORTMODE_720P,EXPORTPATH,SAMPLECOMPOSITIONNAME);
    
  }

}
