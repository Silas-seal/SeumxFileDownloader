
package seumx.file.downloader;

import javafx.application.Application;
import javafx.event.*;
import javafx.scene.*;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.stage.*;

import java.io.*; import java.util.*;
import javafx.collections.*; import java.net.*;import java.util.logging.Level;
import java.util.logging.Logger; import java.awt.Desktop;//import static java.awt.Desktop.getDesktop;
 import javafx.animation.*;
import javafx.util.Duration;
 import org.apache.commons.io.*;


//downloading multiple files   0789 676847
class dwld implements Runnable {
    private URL uur; private File fl; private Thread th;
    @Override 
    public void run(){
        try {
            FileUtils.copyURLToFile(uur, fl); Thread.sleep(70);
        } catch (IOException ex) {
            System.out.println("Input Output error....");
        } catch (InterruptedException ex) {
            Logger.getLogger(dwld.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void setURL(URL url){
        uur=url;
    }
    public void setFile(File file){
        fl=file;
    }
    public void start(){
        if(th==null){
            th=new Thread(this); th.start();
        }
    }
}

public class SeumXFileDownloader extends Application {
    private Label dwn,fname,ddb,ddr,sls; private List<GridPane> sil; private ProgressBar pgb; private GridPane pgbi,pgbi2;
    private Properties pr; private GridPane ddgg; private URL ur; private File file; private ObservableList obk;
    private Desktop dsk; private FadeTransition fdu; private double xoff,yoff;
    
    public static long getFileSize(URL url){
        HttpURLConnection conn=null;
        try {
            conn=(HttpURLConnection) url.openConnection();
            conn.setRequestMethod("HEAD");
            return conn.getContentLengthLong();
        } catch (IOException e ){
            throw new RuntimeException(e);
        } finally {
            if(conn!=null){
                conn.disconnect();
            }
        }
    }
    
    public void trc()  { 
        fdu=new FadeTransition(Duration.millis(13)); fdu.setFromValue(1); fdu.setToValue(1); fdu.setCycleCount(1); fdu.setAutoReverse(false);
        fdu.setNode(pgb); fdu.play();
        fdu.setOnFinished(evv->{ try {
            ddb.setText(String.valueOf(file.length())+"   /   "); ddr.setText(String.valueOf(getFileSize(ur))); 
            double ursz=getFileSize(new URL(ur.toString())); double fsz=new File(file.toString()).length();
            pgb.setProgress(fsz/ursz);
            if(getFileSize(ur)==file.length()){
                //saving the downloaded file to downloads
                try {
                    FileInputStream in=new FileInputStream("dwnd.dll");
                    ObjectInputStream obji=new ObjectInputStream(in);
                    pr=new Properties(); pr=(Properties) obji.readObject(); 
                    pr.put(file.getName(), file.getAbsolutePath());
                    
                    FileOutputStream out=new FileOutputStream("dwnd.dll");
                    ObjectOutputStream objt=new ObjectOutputStream(out);
                    objt.writeObject(pr);
                    
                    //stop process
                    pgb.setProgress(1); fdu.stop(); dwn.setText("Complete"); ddb.setText(String.valueOf(getFileSize(ur))+"  /  ");
                    
                    } catch (FileNotFoundException ex){
                        System.out.println("Error reading dwonload file...");
                    } catch (IOException | ClassNotFoundException ex){
                        System.out.println("Error reading dwonload file...");
                    }
            } else {
                trc();
            }   } catch (MalformedURLException ex) {
                System.out.println("MalformedURLException");
            }
        }); 
    }
    
    @Override
    public void start(Stage Stage) throws Exception {
        //stage pr
        Image icon =new Image(new FileInputStream("icon.png"));
        Stage.getIcons().add(icon); Stage.setWidth(633); Stage.setHeight(357);  Stage.initStyle(StageStyle.UNDECORATED);
        Stage.setResizable(false); Stage.setTitle("SeumX File Downloader");
        Label co=new Label("                SeumX File Downloader"); co.setStyle("-fx-font:bold 21px 'Cambria'; -fx-text-fill:aliceblue");
        Button clc=new Button("X"); clc.setStyle("-fx-font:normal 18px 'Cambria'; -fx-text-fill:black; -fx-background-color:lightsteelblue;");
        clc.setOnMouseMoved(ev->{
            clc.setStyle("-fx-font:normal 18px 'Cambria'; -fx-text-fill:black; -fx-background-color:deeppink;");
        });
        clc.setOnMouseExited(ev->{
            clc.setStyle("-fx-font:normal 18px 'Cambria'; -fx-text-fill:black; -fx-background-color:lightsteelblue;");
        });
        clc.setOnAction(ev->{
            Stage.close();
        });
        
        Label url=new Label("Enter download URL: "); url.setStyle("-fx-font:normal 15px 'Cambria'");
        Label ftyp=new Label("Select File Type: "); ftyp.setStyle("-fx-font:normal 15px 'Cambria'");
        Label fext=new Label("Select Extension: "); fext.setStyle("-fx-font:normal 15px 'Cambria'");
        Label dest=new Label("File Destination: "); dest.setStyle("-fx-font:normal 15px 'Cambria'");
        
        Button dwnld=new Button("Download"); dwnld.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
        Button dwnldg=new Button("Downloading"); dwnldg.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
        Button inp=new Button("In Process"); inp.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
        Button dwnldd=new Button("Downloaded"); dwnldd.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black");
        Button dwnd=new Button("download"); dwnd.setStyle("-fx-font:normal 17px 'Cambria'; -fx-text-fill:azure; -fx-background-color:navy");
        dwnld.setPrefWidth(157); dwnldg.setPrefWidth(157); dwnldd.setPrefWidth(157); dwnd.setPrefWidth(187); inp.setPrefWidth(157);
        dwnld.setAlignment(Pos.CENTER_LEFT); dwnldg.setAlignment(Pos.CENTER_LEFT); dwnldd.setAlignment(Pos.CENTER_LEFT); inp.setAlignment(Pos.CENTER_LEFT);
        Button br=new Button("..."); br.setStyle("-fx-font:bold 12px 'Cambria'; -fx-background-color:black; -fx-text-fill:azure;");
        
        dwnld.setOnMouseMoved(er->{ dwnld.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:azure; -fx-background-color:navy"); }); 
        dwnldg.setOnMouseMoved(er->{ dwnldg.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:azure; -fx-background-color:navy"); }); 
        inp.setOnMouseMoved(er->{ inp.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:azure; -fx-background-color:navy"); }); 
        dwnldd.setOnMouseMoved(er->{ dwnldd.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:azure; -fx-background-color:navy"); }); 
        
        dwnld.setOnMouseExited(er->{ dwnld.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black"); }); 
        dwnldg.setOnMouseExited(er->{ dwnldg.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black"); }); 
        inp.setOnMouseExited(er->{ inp.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black"); }); 
        dwnldd.setOnMouseExited(er->{ dwnldd.setStyle("-fx-font:normal 15px 'Cambria'; -fx-text-fill:azure; -fx-background-color:black"); }); 
        
        //ImageView vw=new ImageView(icon); vw.setFitWidth(70); vw.setFitHeight(70);
        TextField t1=new TextField(); t1.setStyle("-fx-font:normal 15px 'Cambria'");
        TextField t2=new TextField(); t2.setStyle("-fx-font:normal 15px 'Cambria'"); t2.setEditable(false);
        TextField t3=new TextField(); t3.setStyle("-fx-font:normal 15px 'Cambria'");
        t3.setPromptText("Specify Other e.g .mp3");
        TextField t4=new TextField(); t4.setStyle("-fx-font:normal 15px 'Cambria'");
        ComboBox<String> ftp=new ComboBox(); ftp.setStyle("-fx-font:normal 13px 'Cambria'"); ftp.setPrefWidth(187);
        ComboBox<String> fex=new ComboBox(); fex.setStyle("-fx-font:normal 13px 'Cambria'"); fex.setPrefWidth(187);
        ObservableList<String> ob=FXCollections.observableArrayList(); 
        ob.addAll("APPLICATION","ACHIEVE","DOCUMENT","VIDEO","AUDIO","IMAGE","OTHER"); ftp.setItems(ob);
        
        GridPane pn1=new GridPane(); pn1.setPadding(new Insets(2,2,2,2)); pn1.setVgap(3); pn1.setHgap(3); pn1.setAlignment(Pos.TOP_CENTER);
        pn1.setStyle("-fx-background-color:black; -fx-border-color:black"); pn1.add(dwnld,0,0); pn1.add(dwnldg,0,1); pn1.add(dwnldd,0,2);
        
        GridPane pn2=new GridPane(); pn2.setPadding(new Insets(2,2,2,2)); pn2.setVgap(7); pn2.setHgap(27); pn2.setAlignment(Pos.CENTER_RIGHT);
        pn2.setStyle("-fx-background-color:navy"); pn2.add(co,0,0);  pn2.add(clc,4,0);
        
        GridPane pn3=new GridPane(); pn3.setPadding(new Insets(2,2,2,2)); pn3.setVgap(17); pn3.setHgap(7); pn3.setAlignment(Pos.TOP_CENTER);
        pn3.setStyle("-fx-background-color:lightgrey"); pn3.add(pn2,0,0); pn3.add(url,0,1); pn3.add(ftyp,0,2); pn3.add(fext,0,3);
        pn3.add(dest,0,4); pn3.add(t1,1,1); pn3.add(ftp,1,2); pn3.add(fex,1,3); pn3.add(t2,1,4); pn3.add(br,2,4); pn3.add(dwnd,1,5);
        
        GridPane pn4=new GridPane(); pn4.setPadding(new Insets(2,2,2,2)); pn4.setVgap(19); pn4.setHgap(7); pn4.setAlignment(Pos.TOP_CENTER);
        pn4.setStyle("-fx-background-color:lightgrey"); pn4.add(pn2,0,0); pn4.add(pn3,0,1);
        
        BorderPane bp=new BorderPane(); bp.setPadding(new Insets(0,0,0,0));
        bp.setCenter(pn4); bp.setLeft(pn1); 
        
        Scene sn=new Scene(bp); Stage.setScene(sn); Stage.show();
        
        //making the window movable
        bp.setOnMousePressed(ev->{
            xoff=ev.getSceneX(); yoff=ev.getSceneY();
        });
        
        bp.setOnMouseDragged(ev->{
            Stage.setX(ev.getScreenX()-xoff); Stage.setY(ev.getScreenY()-yoff);
        });
        
        //downloading panel
        Label ddg=new Label("Downloading"); ddg.setStyle("-fx-font:bold 19px 'Cambria'; -fx-text-fill:navy;");
        ListView vw=new ListView(); vw.setStyle("-fx-control-inner-background:navy; -fx-font:normal 14px 'Cambria'");
        sil=new ArrayList(); 
        VBox hb=new VBox(); hb.setPadding(new Insets(3)); hb.getChildren().addAll(ddg,vw); file=new File(""); 
        
        ftp.setOnAction((ActionEvent ev)->{
            if(ftp.getValue().equals("OTHER")){
                pn3.getChildren().remove(fex); pn3.add(t3,1,3);
            } else {
                //reading available formats
                List<String> lsst=new ArrayList();
                try {
                    FileInputStream in=new FileInputStream("formats.dll");
                    ObjectInputStream obj=new ObjectInputStream(in);
                    pr=new Properties(); pr=(Properties) obj.readObject(); Set set=pr.keySet();
                    Iterator it=set.iterator(); 
                    while(it.hasNext()){
                         lsst.add(it.next().toString());
                    }
                    List<String> lk=new ArrayList();
                    for(int i=0; i<lsst.size(); i++){
                        if(lsst.get(i).contains(ftp.getValue())){
                            lk.add(lsst.get(i)); //System.out.println(lsst.get(i));
                        }
                    }
                    ObservableList<String> obsr=FXCollections.observableArrayList(lk); fex.setItems(obsr);
                    
                } catch (FileNotFoundException ex){
                    System.out.println("Error getting file....");
                } catch (IOException | ClassNotFoundException ex){
                    System.out.println("Error getting file....");
                }
                
                
                pn3.getChildren().remove(t3); pn3.add(fex,1,3);
            }
        });
        
        fex.setOnAction((ActionEvent ev)->{
            
        });
        
        br.setOnAction((ActionEvent ev)->{
            //reading fmts again
            try {
                FileInputStream in=new FileInputStream("formats.dll");
                ObjectInputStream obj=new ObjectInputStream(in);
                pr=new Properties(); pr=(Properties) obj.readObject();
            } catch (FileNotFoundException ex){
                System.out.println("Error reading formats...");
            } catch (IOException | ClassNotFoundException ex){
                System.out.println("Error reading formats...");
            }
            FileChooser ch=new FileChooser(); String tt=fex.getValue(); String ko=pr.getProperty(tt);
            ch.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(tt, ko));
            File fl=ch.showSaveDialog(Stage);
            if(fl!=null){
                t2.setText(fl.getAbsolutePath());
            }
        });
        
        dwnd.setOnAction((ActionEvent ev)->{ 
            if(url.getText().length()>9 && t2.getText()!=null){
            try {
                ur=new URL(t1.getText());
            } catch (MalformedURLException ex) {
                Logger.getLogger(SeumXFileDownloader.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            file=new File(t2.getText()); 
            dwn=new Label("downloading - "); fname=new Label(file.getName());
            ddb=new Label(String.valueOf(file.length())+"   /   "); ddr=new Label(String.valueOf(getFileSize(ur))); 
            pgb=new ProgressBar(); pgb.setProgress(-1); pgb.setPrefWidth(417); pgb.setStyle("-fx-progress-color:azure");
            pgbi=new GridPane(); pgbi.setHgap(9); pgbi.setPadding(new Insets(2,2,2,2)); pgbi.add(dwn,0,0); pgbi.add(fname,1,0);
            pgbi2=new GridPane(); pgbi2.setHgap(3); pgbi2.setPadding(new Insets(2,2,2,2)); pgbi2.add(ddb,0,0); pgbi2.add(ddr,1,0);
            
            ddgg=new GridPane(); ddgg.setPadding(new Insets(2,2,2,2)); ddgg.setVgap(7); ddgg.setHgap(7); ddgg.setAlignment(Pos.CENTER);
            ddgg.add(pgbi,0,0); ddgg.add(pgb,0,1); ddgg.add(pgbi2,0,2);
            
            sil.add(ddgg); obk=FXCollections.observableArrayList(sil); vw.setItems(obk);
            
            //downloading file by calling downloader app
            try{
                FileOutputStream outy=new FileOutputStream("file.dll");
                ObjectOutputStream objy=new ObjectOutputStream(outy);
                objy.writeObject(file); objy.flush();
                
                FileOutputStream outy2=new FileOutputStream("url.dll");
                ObjectOutputStream objy2=new ObjectOutputStream(outy2);
                objy2.writeObject(ur); objy2.flush();
                
                //starting download app
                dsk=Desktop.getDesktop(); File fgh=new File("Download.jar"); dsk.open(fgh);
            } catch (IOException ex){
                System.out.println("IO error.....");
            }
            //tracking the download progress
            trc();
            
            //clear fields
            t1.setText(""); t2.setText(""); fex.setValue(""); ftp.setValue("");
            
            }
        });
        
        dwnld.setOnAction((ActionEvent ev)->{
            bp.setCenter(pn4);
        });
        
        dwnldg.setOnAction((ActionEvent er)->{
            bp.setCenter(hb);
        });
        
        dwnldd.setOnAction((ActionEvent ev)->{
            //reading the downloaded file
            try {
                FileInputStream ind=new FileInputStream("dwnd.dll");
                ObjectInputStream objs=new ObjectInputStream(ind);
                pr=new Properties(); pr=(Properties) objs.readObject();
                Set stt=pr.keySet(); Iterator it=stt.iterator(); 
                List<String> yt=new ArrayList(); 
                
                while(it.hasNext()){
                    yt.add(pr.get(it.next()).toString());
                }
                ObservableList<String> obb=FXCollections.observableArrayList(yt); ListView vty=new ListView(obb);
                vty.setStyle("-fx-control-inner-background:purple; -fx-font: normal 15px 'Cambria';");
                
                /*vty.setCellFactory(yut->{
                    ListCell<String> cell=new ListCell();
                    ContextMenu cui=new ContextMenu();
                    MenuItem opn=new MenuItem("Open File"); opn.setStyle("-fx-font:normal 14px 'Cambria'");
                    MenuItem rml=new MenuItem("Remove from list"); rml.setStyle("-fx-font:normal 14px 'Cambria'");
                    MenuItem dtl=new MenuItem("Details"); dtl.setStyle("-fx-font:normal 14px 'Cambria'");
                    
                    opn.setOnAction((ActionEvent v)->{
                        
                    });
                    
                    rml.setOnAction((ActionEvent v)->{
                        
                    });
                    
                    dtl.setOnAction((ActionEvent v)->{
                        
                    });
                    
                    cui.getItems().addAll(opn,rml,dtl);
                    cell.textProperty().bind(cell.itemProperty());
                    cell.emptyProperty().addListener((obs, WasEmpty, IsNowEmpty)->{
                        if(IsNowEmpty){
                            opn.setDisable(true); rml.setDisable(true); dtl.setDisable(true);
                        } else {
                            opn.setDisable(false); rml.setDisable(false); dtl.setDisable(false); 
                        }
                    });
                    return cell;
                });*/
                
                Label dwd=new Label("Downloaded Files"); dwd.setStyle("-fx-font: bold 19px 'Cambria'; -fx-text-fill:navy");
                VBox vb=new VBox(); vb.setPadding(new Insets(4)); vb.getChildren().addAll(dwd,vty); bp.setCenter(vb);
                
            } catch (IOException | ClassNotFoundException ex){
                System.out.println("IO errors...........");
            }
        });
        
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}
