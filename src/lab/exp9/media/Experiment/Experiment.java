import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.*;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.applet.MainFrame; 
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.image.TextureLoader;
import javax.swing.JOptionPane;
import com.sun.j3d.utils.behaviors.mouse.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.io.Serializable;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.applet.Applet;
import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.event.*;
import java.awt.GraphicsConfiguration;
import com.sun.j3d.utils.applet.MainFrame; 
import com.sun.j3d.utils.geometry.*;
import com.sun.j3d.utils.universe.*;
import javax.media.j3d.*;
import javax.vecmath.*;
import com.sun.j3d.utils.image.TextureLoader;
import javax.swing.JOptionPane;
//import com.sun.j3d.utils.behaviors.mouse.*;
import com.sun.j3d.utils.behaviors.mouse.MouseRotate;
import com.sun.j3d.utils.behaviors.mouse.MouseWheelZoom;
import java.io.*;
import java.util.*;
import java.awt.*;
import java.io.Serializable;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.BoxLayout;
import javax.swing.JApplet;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.JComboBox; 
import javax.swing.JTextArea; 

class Object extends JFrame {
	public Appearance createAppearance(String file){
		Color3f black = new Color3f(0.0f, 0.0f, 0.0f);

		Color3f white = new Color3f(1.0f, 1.0f, 1.0f);

		Color3f red = new Color3f(0.7f, .15f, .15f);
		TextureLoader loader = new TextureLoader(file,
				"LUMINANCE", new Container());
		Texture texture = loader.getTexture();
		texture.setBoundaryModeS(Texture.WRAP);
		texture.setBoundaryModeT(Texture.WRAP);
		texture.setBoundaryColor(new Color4f(0.0f, 0.0f, 0.0f, 1.0f));

		TextureAttributes texAttr = new TextureAttributes();
		texAttr.setTextureMode(TextureAttributes.MODULATE);
		Appearance appear = new Appearance();
		if(file == "soil.jpg"){
			Color3f color1 = new Color3f (0.6123f, 0.3f, 0.08f);
			ColoringAttributes color1ca = new ColoringAttributes (color1, 1);
			appear.setColoringAttributes(color1ca);
		}
		appear.setTexture(texture);
		appear.setTextureAttributes(texAttr);
		return appear;
	}
	public BranchGroup spikes(float x , float y){
		BranchGroup objRoot = new BranchGroup();
		Transform3D translate = new Transform3D();
		translate.set(new Vector3f(x, y, -0.01f));
		TransformGroup obj = new TransformGroup(translate);
		int primflags = Primitive.GENERATE_NORMALS
			+ Primitive.GENERATE_TEXTURE_COORDS;
		Box box2 = new Box(0.02f, 0.02f, -0.01f, primflags,createAppearance("test.jpg"));
		obj.addChild(box2);
		objRoot.addChild(obj);
		objRoot.compile();
		return objRoot;


	}
	public BranchGroup squareplate(){
		BranchGroup objRoot = new BranchGroup();
		Appearance appearance = createAppearance("test.jpg");
		int primflags = Primitive.GENERATE_NORMALS
			+ Primitive.GENERATE_TEXTURE_COORDS;
		Box box1 = new Box(0.3f, 0.15f, -0.01f, primflags,createAppearance("test.jpg"));
		int i;
		float x , y;
		x = 0.28f;
		y = 0.17f;
		for(i = 0 ; i < 9 ; i ++){
			objRoot.addChild(spikes(x,y));
			x = x - 0.07f;
		}
		objRoot.addChild(box1);
		objRoot.compile();
		return objRoot;

	}

	public BranchGroup Cspikes(float r , float Q , float x , float y,float z ,String file){
		double radians = Math.toRadians(Q);
		BranchGroup objRoot = new BranchGroup(); 

		int primflags = Primitive.GENERATE_NORMALS
			+ Primitive.GENERATE_TEXTURE_COORDS;
		Box c = new Box(x,y, 0.01f,primflags, createAppearance(file));
		TransformGroup cctg = new TransformGroup(); 	// a TransformGroup for the ColorCube called cctg
		cctg.addChild(c); 			// add ColorCube to cctg

		Transform3D cc3d = new Transform3D(); // a Transform3D allows a TransformGroup to move
		cc3d.setTranslation(new Vector3f (0.0f ,0.0f ,z )); // set translation to x=0.8, y=1.0, z= -2.0

		cctg.setTransform(cc3d); // set Transform for TransformGroup

		objRoot.addChild(cctg);
		cc3d.setTranslation(new Vector3f(0,0,0));
		cctg.setTransform(cc3d);
		Transform3D someRotation = new Transform3D();
		someRotation.rotZ(radians);
		cc3d.mul(someRotation);
		cc3d.setTranslation(new Vector3f (r*(float)Math.cos(radians) ,r*(float)Math.sin(radians) ,0.0f ));
		cctg.setTransform(cc3d);
		return objRoot;	
	}
	public BranchGroup circularplate(){

		BranchGroup objRoot = new BranchGroup();
		Transform3D translate = new Transform3D();
		translate.rotX(Math.PI/2);
		TransformGroup obj = new TransformGroup(translate);
		int primflags = Primitive.GENERATE_NORMALS
			+ Primitive.GENERATE_TEXTURE_COORDS;

		obj.addChild(new Cylinder(0.2f,0.001f,primflags,50,10,createAppearance("test.jpg")));
		float Q = 0.0f;
		for ( Q = 1.0f ; Q < 360.0f ; Q = Q + 20.0f){
			objRoot.addChild(Cspikes(0.19f,Q,0.02f,0.02f,-0.01f,"test.jpg"));	
		}
		objRoot.addChild(obj);
		objRoot.compile();
		return objRoot;


	}
	public BranchGroup handp(float factor){
		BranchGroup objRoot = new BranchGroup();
		Appearance appear = new Appearance();
		Color3f color1 = new Color3f (0.0f, 0.0f, 0.0f);
		ColoringAttributes color1ca = new ColoringAttributes (color1, 1);
		appear.setColoringAttributes(color1ca);
		Box box1 = new Box(0.01f, 0.05f, 0.0001f, appear);
		Box box2 = new Box(0.01f, 0.05f, 0.01f, appear);
		Transform3D translat3 = new Transform3D();
		if(factor > 0.0f)
			translat3.rotZ((float)Math.PI/factor);
		translat3.setScale(new Vector3d(0.1f,0.1f,1f));
		if(factor == 0)
			factor = 4.0f;
		translat3.setTranslation(new Vector3d(-0.113f, -0.34f + factor*0.0015f, 0.05f));
		TransformGroup obj3 = new TransformGroup(translat3);
		obj3.addChild(box1);
		objRoot.addChild(obj3);
		return objRoot;	
	}

	public BranchGroup hand(float factor){
		BranchGroup objRoot = new BranchGroup();
		Appearance appear = new Appearance();
		Color3f color1 = new Color3f (0.0f, 0.0f, 0.0f);
		ColoringAttributes color1ca = new ColoringAttributes (color1, 1);
		appear.setColoringAttributes(color1ca);
		Box box1 = new Box(0.01f, 0.05f, 0.0001f, appear);
		Box box2 = new Box(0.01f, 0.05f, 0.01f, appear);
		Transform3D translat3 = new Transform3D();
		if(factor > 0.0f)
			translat3.rotZ((float)Math.PI/factor);
		translat3.setScale(new Vector3d(0.1f,0.1f,1f));
		if(factor == 0)
			factor = 4;
		translat3.setTranslation(new Vector3d(0.113f, -0.34f + factor*0.0015f, 0.05f));
		TransformGroup obj3 = new TransformGroup(translat3);
		obj3.addChild(box1);
		objRoot.addChild(obj3);
		return objRoot;	
	}
/*	public BranchGroup handg(){

		BranchGroup objRoot = new BranchGroup();
		Appearance appear = new Appearance();
		Color3f color1 = new Color3f (0.0f, 0.0f, 0.0f);
		ColoringAttributes color1ca = new ColoringAttributes (color1, 1);
		appear.setColoringAttributes(color1ca);
		int primflags = Primitive.GENERATE_NORMALS
			+ Primitive.GENERATE_TEXTURE_COORDS;
		Point3f[] plaPts = new Point3f[2];
		plaPts[0] = new Point3f(0.0f, 0.0f, 0.0f);
		plaPts[1] = new Point3f(00.0f, 0.2f, 0.0f);
		LineArray pla = new LineArray(2, LineArray.COORDINATES);
		pla.setCoordinates(0, plaPts);
		Shape3D plShape = new Shape3D(pla, appear);
		Box box1 = new Box(0.002f, 0.05f, 0.01f,primflags, appear);
		Transform3D translate3 = new Transform3D();
		translate3.set(new Vector3f(0.0f, 0.05f, 0.05f));
		TransformGroup obj3 = new TransformGroup(translate3);
		obj3.addChild(box1);
		objRoot.addChild(plShape);
		return objRoot;	

	}*/
	public BranchGroup handg(){

		BranchGroup objRoot = new BranchGroup();
		Appearance appear = new Appearance();
		Color3f color1 = new Color3f (0.0f, 0.0f, 0.0f);
		ColoringAttributes color1ca = new ColoringAttributes (color1, 1);
		appear.setColoringAttributes(color1ca);
		Box box1 = new Box(0.01f, 0.05f, 0.01f, appear);
		Transform3D translate3 = new Transform3D();
		translate3.set(new Vector3f(0.0f, 0.05f, 0.0f));
		TransformGroup obj3 = new TransformGroup(translate3);
		obj3.addChild(box1);
		objRoot.addChild(obj3);
		return objRoot;	

	}
	public BranchGroup gauge(){
		BranchGroup objRoot = new BranchGroup();
		Appearance appear = new Appearance();
		Color3f color1 = new Color3f (0.7f, 0.7f, 0.7f);
		ColoringAttributes color1ca = new ColoringAttributes (color1, 1);
		appear.setColoringAttributes(color1ca);
		Transform3D translate = new Transform3D();
		translate.rotX(Math.PI/2);
		TransformGroup obj = new TransformGroup(translate);
		obj.addChild(new Cylinder(0.2f,0.001f,1,50,10,appear));
		float Q = 0.0f;
		for ( Q = 1.0f ; Q < 360.0f ; Q = Q + 20.0f){
			objRoot.addChild(Cspikes(0.18f,Q,0.01f,0.005f,0.01f,"black.jpg"));	
		}
		objRoot.addChild(obj);
		Appearance appear1 = new Appearance();
		Color3f color = new Color3f (0.0f, 0.0f, 0.0f);
		ColoringAttributes colorca = new ColoringAttributes (color, 1);
		appear1.setColoringAttributes(colorca);
		obj.addChild(new Cylinder(0.00001f,0.1f,1,10,60,appear1));
		objRoot.compile();
		return objRoot;
	}
	public BranchGroup hydraulicJack(){


		BranchGroup objRoot = new BranchGroup();
		int primflags = Primitive.GENERATE_NORMALS
			+ Primitive.GENERATE_TEXTURE_COORDS;
		Appearance appear = new Appearance();
		Color3f color1 = new Color3f (0.0f, 1.0f, 1.0f);
		ColoringAttributes color1ca = new ColoringAttributes (color1, 1);
		appear.setColoringAttributes(color1ca);
		Box box1 = new Box(0.05f, 0.2f, -0.01f,primflags, appear);
		objRoot.addChild(box1);
		Transform3D translate = new Transform3D();
		translate.set(new Vector3f(0.009f, 0.25f, -0.01f));
		TransformGroup obj = new TransformGroup(translate);
		Box box2 = new Box(0.025f, 0.05f, -0.01f, primflags,appear);
		obj.addChild(box2);
		objRoot.addChild(obj);
		Transform3D translate1 = new Transform3D();
		translate1.set(new Vector3f(0.009f, 0.31f, -0.01f));
		TransformGroup obj1 = new TransformGroup(translate1);
		Box box3 = new Box(0.1f, 0.01f, -0.01f, primflags,appear);
		obj1.addChild(box3);
		objRoot.addChild(obj1);
		objRoot.compile();
		return objRoot;
	}
	public BranchGroup background(){
		BranchGroup objRoot = new BranchGroup();
		return objRoot;

	}
	public BranchGroup Text(){
		BranchGroup objRoot = new BranchGroup();
		Transform3D translat3 = new Transform3D();
		translat3.set(new Vector3f(-0.97f, 0.58f, 0.0f));
		TransformGroup ob3 = new TransformGroup(translat3);
		Text2D tex = new Text2D("Objective - To determine the allowable bearing capacity of soil", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 14, Font.BOLD);	
		ob3.addChild(tex);
		objRoot.addChild(ob3);
		Transform3D translate3 = new Transform3D();
		translate3.set(new Vector3f(-0.1f, 0.4f, 0.0f));
		TransformGroup obj3 = new TransformGroup(translate3);
		Text2D text = new Text2D("APPARATUS", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 13, Font.BOLD);	
		obj3.addChild(text);
		objRoot.addChild(obj3);
		Transform3D translate2 = new Transform3D();
		translate2.set(new Vector3f(-1.0f, 0.05f, 0.0f));
		TransformGroup obj2 = new TransformGroup(translate2);
		Text2D text1 = new Text2D("Square Bearing Plate Of Size 30cm", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 12, Font.BOLD);	
		obj2.addChild(text1);
		objRoot.addChild(obj2);
		Transform3D translate1 = new Transform3D();
		translate1.set(new Vector3f(-0.08f, -0.05f, 0.0f));
		TransformGroup obj1 = new TransformGroup(translate1);
		Text2D text2 = new Text2D("Circular Bearing Plate Of Diameter 30cm", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 12, Font.BOLD);	
		obj1.addChild(text2);
		objRoot.addChild(obj1);

		Transform3D translate = new Transform3D();
		translate.set(new Vector3f(0.0f, -0.5f, 0.0f));
		TransformGroup obj = new TransformGroup(translate);
		Text2D text3 = new Text2D("Hydraulic Jack", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 12, Font.BOLD);	
		obj.addChild(text3);
		objRoot.addChild(obj);
		Transform3D translate0 = new Transform3D();
		translate0.set(new Vector3f(-0.65f, -0.52f, 0.0f));
		TransformGroup obj0 = new TransformGroup(translate0);
		Text2D text4 = new Text2D("Dial Gauge", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 12, Font.BOLD);	
		obj0.addChild(text4);
		objRoot.addChild(obj0);
		objRoot.compile();
		return objRoot;


	}
	public BranchGroup GroundWithPlate(){

		int p = Primitive.GENERATE_NORMALS
			+ Primitive.GENERATE_TEXTURE_COORDS;
		BranchGroup objRoot = new BranchGroup();
		Transform3D translate = new Transform3D();
		translate.set(new Vector3f(-0.7f, -0.6f, -0.2f));
		TransformGroup obj = new TransformGroup(translate);
		Box box1 = new Box(0.4f, 0.1f, 0.1f,createAppearance("test.jpg"));
		obj.addChild(box1);
		objRoot.addChild(obj);
		Transform3D translate1 = new Transform3D();
		translate1.set(new Vector3f(0.7f, -0.6f, -0.2f));
		TransformGroup obj1 = new TransformGroup(translate1);
		Box box2 = new Box(0.4f, 0.1f, 0.1f, createAppearance("test.jpg"));
		obj1.addChild(box2);
		objRoot.addChild(obj1);
		Transform3D translate2 = new Transform3D();
		translate2.set(new Vector3f(0.0f, -0.8f, -1.0f));
		TransformGroup obj2 = new TransformGroup(translate2);
		Box box3 = new Box(0.5f, 0.000000005f, 0.2f,p, createAppearance("soil.jpg"));
		obj2.addChild(box3);
		objRoot.addChild(obj2);
		Transform3D translate3 = new Transform3D();
		translate3.set(new Vector3f(0.0f, -0.8f, -2.5f));
		TransformGroup obj3 = new TransformGroup(translate3);
		Box box4 = new Box(0.6f, 0.2f, 0.001f,p, createAppearance("soil.jpg"));
		obj3.addChild(box4);
		objRoot.addChild(obj3);
		Transform3D translate4 = new Transform3D();
		translate4.set(new Vector3f(-0.55f, -0.8f, -2.5f));
		TransformGroup obj4 = new TransformGroup(translate4);
		Box box5 = new Box(0.02f, 0.2f, 0.1f,p, createAppearance("soil.jpg"));
		obj4.addChild(box5);
		objRoot.addChild(obj4);
		Transform3D translate5 = new Transform3D();
		translate5.set(new Vector3f(0.55f, -0.8f, -2.5f));
		TransformGroup obj5 = new TransformGroup(translate5);
		Box box6 = new Box(0.02f, 0.2f, 0.1f,p, createAppearance("soil.jpg"));
		obj5.addChild(box6);
		objRoot.addChild(obj5);
		objRoot.compile();
		return objRoot;

	}
	public BranchGroup createBox(Vector3d pos, Vector3d scale, Vector3d rot, Color3f colr, String texfile) {
		Transform3D t = new Transform3D();
		BranchGroup objRoot = new BranchGroup();
		float rad = 0.01745329F;
		if (rot.x != 0.0D) {
			t.rotX((double)rad * rot.x);
		} 
		else if (rot.y != 0.0D) {
			t.rotY((double)rad * rot.y);
		} 
		else if (rot.z != 0.0D) {
			t.rotZ((double)rad * rot.z);
		}
		t.setScale(scale);
		t.setTranslation(pos);
		TransformGroup objtrans = new TransformGroup(t);
		objtrans.setCapability(18);
		objtrans.setCapability(17);
		Appearance app = new Appearance();
		ColoringAttributes ca = new ColoringAttributes();
		ca.setColor(colr);
		app.setColoringAttributes(ca);
		int p = 10;

		if(texfile == "test.jpg"){
			p = 0;
			TransparencyAttributes transAttrs = new TransparencyAttributes(
					TransparencyAttributes.FASTEST, 0.2f);
			app.setTransparencyAttributes(transAttrs);
		}
		if (texfile != null) {
			javax.media.j3d.Texture tex = (new TextureLoader(texfile, 6, this)).getTexture();
			app.setTexture(tex);
			TextureAttributes texAttr = new TextureAttributes();
			texAttr.setTextureMode(2);
			app.setTextureAttributes(texAttr);
		}
		objtrans.addChild(new Box(1.0F, 1.0F, 1.0F, p, app));
		objRoot.addChild(objtrans);
		return objRoot;
	}

	public BranchGroup world(){

		BranchGroup objRoot = new BranchGroup();
		objRoot.addChild(createBox(new Vector3d(1,0.1f,0), new Vector3d(0.05,0.7f,2),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"cloud.jpg"));
		objRoot.addChild(createBox(new Vector3d(-1,0.1f,0), new Vector3d(0.05,0.7f,2),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"cloud.jpg"));
		objRoot.addChild(createBox(new Vector3d(0,0.1f,-2.0f), new Vector3d(1,0.7f,0.05),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"cloud.jpg"));
		objRoot.addChild(createBox(new Vector3d(0,0.84f,0), new Vector3d(1.05,0.04f,2),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"cloud.jpg"));
		objRoot.addChild(createBox(new Vector3d(0,-0.64f,-2.5f), new Vector3d(1.05,0.1f,2),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"soil.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.7,-0.64f,-0.5f), new Vector3d(0.4,0.1f,0.7),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"soil.jpg"));
		objRoot.addChild(createBox(new Vector3d(-0.7,-0.64f,-0.5f), new Vector3d(0.4,0.1f,0.7),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"soil.jpg"));
		objRoot.addChild(createBox(new Vector3d(0,-0.74f,-0.15f), new Vector3d(0.4,0.01f,0.3),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"test.jpg"));
		objRoot.addChild(createBox(new Vector3d(-0.3,-0.74f,-0.1f), new Vector3d(0.01,0.01f,0.4f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"test.jpg"));
		objRoot.addChild(createBox(new Vector3d(-0.3,-0.54f,-0.1f), new Vector3d(0.01,0.01f,0.4f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"test.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.3,-0.74f,-0.1f), new Vector3d(0.01,0.01f,0.4f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"test.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.3,-0.54f,-0.1f), new Vector3d(0.01,0.01f,0.4f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"test.jpg"));
		objRoot.addChild(createBox(new Vector3d(0,-0.54f,-0.5f), new Vector3d(0.3f,0.01f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"test.jpg"));
		objRoot.addChild(createBox(new Vector3d(0,-0.74f,-0.5f), new Vector3d(0.3f,0.01f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"test.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.3f,-0.64f,0.2f), new Vector3d(0.01f,0.1f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"test.jpg"));
		objRoot.addChild(createBox(new Vector3d(-0.3f,-0.64f,-0.5f), new Vector3d(0.01f,0.1f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"test.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.3f,-0.64f,-0.5f), new Vector3d(0.01f,0.1f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"test.jpg"));
		objRoot.addChild(createBox(new Vector3d(-0.3f,-0.64f,0.2f), new Vector3d(0.01f,0.1f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"test.jpg"));
		objRoot.addChild(createBox(new Vector3d(-0.29f,-0.64f,-0.12f), new Vector3d(0.01f,0.1f,0.3f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"earth.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.29f,-0.64f,-0.12f), new Vector3d(0.01f,0.1f,0.3f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"earth.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.0f,-0.64f,-0.5f), new Vector3d(0.3f,0.1f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"earth.jpg"));
		objRoot.compile();
		return objRoot;
	}
	public BranchGroup bottomplate(){

		BranchGroup objRoot = new BranchGroup();

		objRoot.addChild(createBox(new Vector3d(0.0f,-0.72f,-0.2f), new Vector3d(0.15f,0.05f,0.1f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"newtest.jpg"));

		objRoot.compile();
		return objRoot;
	}
	public BranchGroup worldtext(){
		BranchGroup objRoot = new BranchGroup();
		Transform3D translate = new Transform3D();
		translate.set(new Vector3f(-0.27f, -0.75f, 0.25f));
		TransformGroup obj = new TransformGroup(translate);
		Text2D text = new Text2D("<--5 X Bearing plate-->", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 10, Font.BOLD);	
		obj.addChild(text);
		objRoot.addChild(obj);
		Transform3D translate1 = new Transform3D();
		translate1.set(new Vector3f(0.0f, -0.55f, 0.32f));
		TransformGroup obj1 = new TransformGroup(translate1);
		Text2D text1 = new Text2D("Datum Bar or as per required", new Color3f(0f, 0f, 0.0f),"Courier", 10, Font.BOLD);	
		obj1.addChild(text1);
		objRoot.addChild(obj1);
		Transform3D translate2 = new Transform3D();
		translate2.set(new Vector3f(-0.6f, -1.0f, 0.05f));
		TransformGroup obj2 = new TransformGroup(translate2);
		Text2D text2 = new Text2D("Test Plate 5mm deep into ground level", new Color3f(0f, 0f, 0f),"Courier", 12, Font.BOLD);	
		obj2.addChild(text2);
		objRoot.addChild(obj2);
		/*	Transform3D translate3 = new Transform3D();
			translate3.set(new Vector3f(-0.11f, -0.84f, 0.05f));
			TransformGroup obj3 = new TransformGroup(translate3);
			Text2D text3 = new Text2D("5 mm Deep into", new Color3f(0f, 0f, 0f),"Courier", 12, Font.BOLD);	
			obj3.addChild(text3);
			objRoot.addChild(obj3);
			Transform3D translate4 = new Transform3D();
			translate4.set(new Vector3f(-0.11f, -0.88f, 0.05f));
			TransformGroup obj4 = new TransformGroup(translate4);
			Text2D text4 = new Text2D("Ground Level", new Color3f(0f, 0f, 0f),"Courier", 12, Font.BOLD);	
			obj4.addChild(text4);
			objRoot.addChild(obj4);*/
		objRoot.addChild(createBox(new Vector3d(0.4f,-0.67f,0.30f), new Vector3d(0.001f,0.09f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"black.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.0f,-0.92f,0.1f), new Vector3d(0.001f,0.09f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"black.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.4f,-0.58f,0.30f), new Vector3d(0.05f,0.001f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"black.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.4f,-0.76f,0.30f), new Vector3d(0.05f,0.001f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"black.jpg"));
		objRoot.compile();
		return objRoot;
	}
	public BranchGroup threeplates (float y ){
		BranchGroup objRoot = new BranchGroup();
		Transform3D translate3 = new Transform3D();
		translate3.set(new Vector3f(0.0f, y, 0.0f));
		TransformGroup obj3 = new TransformGroup(translate3);
		obj3.addChild(createBox(new Vector3d(0.0f,-0.72f,-0.1f), new Vector3d(0.15f,0.05f,0.1f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"newtest.jpg"));
		obj3.addChild(createBox(new Vector3d(0.0f,-0.66f,-0.1f), new Vector3d(0.1f,0.02f,0.08f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"newtest1.jpg"));
		obj3.addChild(createBox(new Vector3d(0.0f,-0.64f,-0.1f), new Vector3d(0.06f,0.01f,0.05f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"newtest2.jpg"));
		objRoot.addChild(obj3);
		objRoot.compile();
		return objRoot;
	}
	public BranchGroup BearingPlates(int flag){

		BranchGroup objRoot = new BranchGroup();
		Appearance appear = new Appearance();
		Color3f color1 = new Color3f (0.0f, 1.0f, 1.0f);
		ColoringAttributes color1ca = new ColoringAttributes (color1, 1);
		appear.setColoringAttributes(color1ca);
		Transform3D translate = new Transform3D();
		translate.set(new Vector3f(0f, -0.58f, -0.22f));
		TransformGroup obj = new TransformGroup(translate);
		obj.addChild(new Cylinder(0.04f,0.3f,1,50,10,appear));
		objRoot.addChild(obj);

		Transform3D translate1 = new Transform3D();
		translate1.set(new Vector3f(0f, -0.38f, -0.22f));
		TransformGroup obj1 = new TransformGroup(translate1);
		obj1.addChild(new Cylinder(0.02f,0.1f,1,50,10,appear));
		objRoot.addChild(obj1);

		Transform3D translate2 = new Transform3D();
		translate2.set(new Vector3f(0f, -0.33f, -0.22f));
		TransformGroup obj2 = new TransformGroup(translate2);
		obj2.addChild(new Cylinder(0.05f,0.008f,1,50,10,appear));
		objRoot.addChild(obj2);

		if(flag ==1)
			objRoot.addChild(createBox(new Vector3d(0.0f,-0.32f,-0.2f), new Vector3d(0.2f,0.01f,0.2f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"newtest.jpg"));
		objRoot.compile();
		return objRoot;
	}
	public BranchGroup DatumGauge(){
		BranchGroup objRoot = new BranchGroup();
		objRoot.addChild(createBox(new Vector3d(-0.11f,-0.54f,-0.2f), new Vector3d(0.01f,0.15f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"pipe.jpg"));
		objRoot.addChild(createBox(new Vector3d(-0.3f,-0.38f,-0.2f), new Vector3d(0.2f,0.01f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"pipe.jpg"));
		objRoot.addChild(createBox(new Vector3d(-0.5f,-0.48f,-0.2f), new Vector3d(0.01f,0.1f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"pipe.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.09f,-0.48f,-0.2f), new Vector3d(0.05f,0.01f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"pipe.jpg"));

		Transform3D translate3 = new Transform3D();
		translate3.setTranslation(new Vector3d(0.16f, -0.48f, -0.2f));
		translate3.setScale(new Vector3d(0.1f,0.1f,1f));
		TransformGroup obj3 = new TransformGroup(translate3);
		obj3.addChild(gauge());
		objRoot.addChild(obj3);

		Transform3D translate4 = new Transform3D();
		translate4.setTranslation(new Vector3d(0.16f, -0.48f, -0.2f));
		translate4.setScale(new Vector3d(0.2f,0.1f,1f));
		TransformGroup obj4 = new TransformGroup(translate4);
		obj4.addChild(handg());
		objRoot.addChild(obj4);

		Transform3D translat3 = new Transform3D();
		translat3.setTranslation(new Vector3d(-0.10f, -0.48f, 0.0f));
		translat3.setScale(new Vector3d(0.1f,0.1f,1f));
		TransformGroup ob3 = new TransformGroup(translat3);
		ob3.addChild(gauge());
		objRoot.addChild(ob3);

		Transform3D translat4 = new Transform3D();
		translat4.setTranslation(new Vector3d(-0.10f, -0.48f, 0.0f));
		translat4.setScale(new Vector3d(0.2f,0.1f,1f));
		TransformGroup ob4 = new TransformGroup(translat4);
		ob4.addChild(handg());
		objRoot.addChild(ob4);
		objRoot.compile();
		return objRoot;
	}
	public BranchGroup bptext(){
		BranchGroup objRoot = new BranchGroup();
		Transform3D translate4 = new Transform3D();
		translate4.set(new Vector3f(0.0f, -0.28f, 0.1f));
		TransformGroup obj4 = new TransformGroup(translate4);
		Text2D text4 = new Text2D("Bearing plate", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 10, Font.BOLD);	
		obj4.addChild(text4);
		objRoot.addChild(obj4);
		Transform3D translate5 = new Transform3D();
		translate5.set(new Vector3f(-0.75f, -0.48f, 0.1f));
		TransformGroup obj5 = new TransformGroup(translate5);
		Text2D text5 = new Text2D("Datum Frame", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 10, Font.BOLD);	
		obj5.addChild(text5);
		objRoot.addChild(obj5);
		Transform3D translate6 = new Transform3D();
		translate6.set(new Vector3f(0.25f, -0.54f, 0.3f));
		TransformGroup obj6 = new TransformGroup(translate6);
		Text2D text6 = new Text2D("Hydraulic Jack", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 10, Font.BOLD);	
		obj6.addChild(text6);
		objRoot.addChild(obj6);
		Transform3D translate7 = new Transform3D();
		translate7.set(new Vector3f(-0.25f, -0.53f, 0.1f));
		TransformGroup obj7 = new TransformGroup(translate7);
		Text2D text7 = new Text2D("Dial", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 10, Font.BOLD);	
		obj7.addChild(text7);
		objRoot.addChild(obj7);
		Transform3D translte7 = new Transform3D();
		translte7.set(new Vector3f(-0.25f, -0.58f, 0.1f));
		TransformGroup bj7 = new TransformGroup(translte7);
		Text2D tet7 = new Text2D("Gauge", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 10, Font.BOLD);	
		bj7.addChild(tet7);
		objRoot.addChild(bj7);
		objRoot.addChild(createBox(new Vector3d(0.15f,-0.50f,0.2f), new Vector3d(0.12f,0.001f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"black.jpg"));
		objRoot.addChild(createBox(new Vector3d(-0.13f,-0.53f,0.2f), new Vector3d(0.02f,0.001f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"black.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.0f,-0.325f,0.2f), new Vector3d(0.001f,0.05f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"black.jpg"));
		objRoot.addChild(createBox(new Vector3d(-0.47f,-0.51f,0.2f), new Vector3d(0.06f,0.001f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"black.jpg"));
		objRoot.compile();
		return objRoot;
	}
	public BranchGroup weight(float x,float l ){

		BranchGroup objRoot = new BranchGroup();
		objRoot.addChild(createBox(new Vector3d(0.0f, x,-0.2f), new Vector3d(l,0.05f,0.3f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"img7.jpg"));
		objRoot.compile();
		return objRoot;
	}
	public BranchGroup Instance(float[] knots,Quat4f[] quats,Point3f[] positions ,float y){

		float movement = 100000*0.01f;
		int temps  = (int)(movement);
		BranchGroup objRoot = new BranchGroup();                      // Create the root of the branch graph
		Alpha alpha = new Alpha(1, 60000);                           // alpha is for moving the point froddm initial to final position
		//    ( 1 is the no of times it is moved and 100000 is the time   
		TransformGroup target = new TransformGroup();                 // target is the thing which moves according to alpha
		Transform3D axisOfRotPos = new Transform3D();           
		target.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);

		AxisAngle4f axis = new AxisAngle4f(0.0f,0.0f,0.0f,0.0f);      // axis tells about angle of rotation about translation axis
		axisOfRotPos.set(axis);



		RotPosPathInterpolator rotPosPath = new RotPosPathInterpolator           
			(alpha, target, axisOfRotPos, knots, quats, positions);  //rotPosPath tells about rotation and translation
		rotPosPath.setSchedulingBounds(new BoundingSphere());

		objRoot.addChild(target);
		objRoot.addChild(rotPosPath);
		target.addChild(threeplates(y));

		objRoot.compile();

		return objRoot;
	} 

	public BranchGroup Platform(){
		BranchGroup objRoot = new BranchGroup();

		objRoot.addChild(createBox(new Vector3d(0.0f,-0.53f,-0.22f), new Vector3d(0.5f,0.01f,0.25f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"platform.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.0f,-0.32f,-0.2f), new Vector3d(0.5f,0.01f,0.25f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"platform.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.4f,-0.44f,-0.2f), new Vector3d(0.1f,0.1f,0.1f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"brick.jpg"));
		objRoot.addChild(createBox(new Vector3d(-0.4f,-0.44f,-0.2f), new Vector3d(0.1f,0.1f,0.1f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"brick.jpg"));
		objRoot.addChild(createBox(new Vector3d(-0.12f,-0.68f,-0.2f), new Vector3d(0.01f,0.1f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"brick.jpg"));
		objRoot.addChild(createBox(new Vector3d(0.12f,-0.68f,-0.2f), new Vector3d(0.01f,0.1f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"brick.jpg"));
		Transform3D translate3 = new Transform3D();
		translate3.setTranslation(new Vector3d(0.12f, -0.56f, -0.2f));
		translate3.setScale(new Vector3d(0.1f,0.1f,1f));
		TransformGroup obj3 = new TransformGroup(translate3);
		obj3.addChild(gauge());
		//	obj3.addChild(hand());
		objRoot.addChild(obj3);
		Transform3D translate2 = new Transform3D();
		translate2.setTranslation(new Vector3d(-0.12f, -0.56f, -0.2f));
		translate2.setScale(new Vector3d(0.1f,0.1f,1f));
		TransformGroup obj2 = new TransformGroup(translate2);
		obj2.addChild(gauge());
		//obj2.addChild(hand());
		objRoot.addChild(obj2);
		objRoot.compile();
		return objRoot;


	}
	public BranchGroup backg(){
		BranchGroup objRoot = new BranchGroup();
		Background bg = new Background();	
		bg.setColor(1f, 1.0f, 1.0f);
		bg.setApplicationBounds(new BoundingSphere());
		objRoot.addChild(bg);
		objRotate.addChild(createBox(new Vector3d(0f,0.0f,-0.5f), new Vector3d(2.2f,2.2f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"bgpage.jpg"));
		return objRoot;
	}
	public void test(int flag){

		try{

			String Sp=JOptionPane.showInputDialog(null,"Enter Sp","1");
			String Bp=JOptionPane.showInputDialog(null,"Enter Bp","1");
			String Bf=JOptionPane.showInputDialog(null,"Enter Bf","1");
			float sp = Float.parseFloat(Sp);
			float bp = Float.parseFloat(Bp);
			float bf = Float.parseFloat(Bf);
			float sf;
			if(flag == 1)
				sf = sp*(bf/bp);
			else{
				sf = (bf*(bp + 0.3f))/(bp*(bf+0.3f));
				sf = sf*sf;
				sf = sf*sp;
			}
			String ret = "Sf = ";
			ret = ret + sf;
			if ( bp == 0)
				ret = "Sf = Infinite";
			JOptionPane.showMessageDialog(null, ret);

		}
		catch (NumberFormatException e) {
			JOptionPane.showMessageDialog(null, "The Input should be float or integer");
		}
		return;		
	}

	public static JButton next = new JButton("Next");
	public static JButton restart = new JButton("Restart");
	public static JButton wnext = new JButton("Add 25% Weight");
	public static JButton rnext = new JButton("Unloading");
	public static JPanel panel1 = new JPanel();
	public static JPanel panel2 = new JPanel();
	public static String[] options = {"Cohesive Soil","Non-Cohesive Soil"};
	public JLabel label = new JLabel("Select Soil");
	public JComboBox list = new JComboBox();
	public BranchGroup content;
	public Canvas3D canvas3D;
	public SimpleUniverse simpleU;
	public GraphicsConfiguration config;
	public	BranchGroup objRotate = new BranchGroup();
	public TransformGroup obj3 , o3;
	public	BranchGroup objRoot = new BranchGroup();
	public	BranchGroup sceen = new BranchGroup(); 
	public	BranchGroup R1 = new BranchGroup(); 
	public	BranchGroup R2 = new BranchGroup(); 
	public	BranchGroup R3 = new BranchGroup(); 
	public	BranchGroup R4 = new BranchGroup(); 
	public	BranchGroup R5 = new BranchGroup(); 
	public	BranchGroup R6 = new BranchGroup(); 
	public	BranchGroup R7 = new BranchGroup(); 
	public	BranchGroup R8 = new BranchGroup(); 
	public	BranchGroup R9 = new BranchGroup(); 
	public	BranchGroup R10 = new BranchGroup(); 
	public	BranchGroup A1 = new BranchGroup(); 
	public	BranchGroup A2 = new BranchGroup(); 
	public	BranchGroup A3 = new BranchGroup(); 
	public	BranchGroup A4 = new BranchGroup(); 
	public	BranchGroup A5 = new BranchGroup(); 
	public	BranchGroup A6 = new BranchGroup(); 
	public	BranchGroup W1 = new BranchGroup(); 
	public	BranchGroup W2 = new BranchGroup(); 
	public	BranchGroup W3 = new BranchGroup(); 
	public	BranchGroup H1 = new BranchGroup(); 
	public	BranchGroup H2 = new BranchGroup(); 
	public	BranchGroup H3 = new BranchGroup(); 
	public	BranchGroup H4 = new BranchGroup(); 
	public	BranchGroup T1 = new BranchGroup(); 
	public	BranchGroup T2 = new BranchGroup(); 
	public	BranchGroup T3 = new BranchGroup(); 
	public	BranchGroup T4 = new BranchGroup(); 
	public	BranchGroup T5 = new BranchGroup(); 
	public	BranchGroup T6 = new BranchGroup(); 
	public	BranchGroup T7 = new BranchGroup(); 
	public	BranchGroup T8 = new BranchGroup(); 
	public	BranchGroup T9 = new BranchGroup(); 
	public	BranchGroup T10 = new BranchGroup(); 
	public	BranchGroup clicknext = new BranchGroup(); 
	public	BranchGroup G1 = new BranchGroup(); 
	public	BranchGroup G2 = new BranchGroup(); 
	public	BranchGroup proc1 = new BranchGroup(); 
	public	BranchGroup proc2 = new BranchGroup(); 
	public	BranchGroup proc3 = new BranchGroup(); 
	public	BranchGroup Result1 = new BranchGroup(); 
	public	BranchGroup Result2 = new BranchGroup(); 
	public	BranchGroup Result3 = new BranchGroup(); 
	public	BranchGroup Result4 = new BranchGroup(); 
	public	BranchGroup I1 = new BranchGroup(); 
	public	BranchGroup I2 = new BranchGroup(); 
	public	BranchGroup BG = new BranchGroup(); 
	public	int count = 0;
	public int w = 0;
	public int soil = 0;
	public int r = 0;
	public BranchGroup createSceneGraph_def(){
		sceen.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);
		sceen.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		sceen.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		sceen.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		sceen.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		sceen.setCapability(BranchGroup.ALLOW_DETACH);
		Result1.setCapability(BranchGroup.ALLOW_DETACH);
		Result2.setCapability(BranchGroup.ALLOW_DETACH);
		Result3.setCapability(BranchGroup.ALLOW_DETACH);
		Result4.setCapability(BranchGroup.ALLOW_DETACH);
		clicknext.setCapability(BranchGroup.ALLOW_DETACH);
		I1.setCapability(BranchGroup.ALLOW_DETACH);
		I2.setCapability(BranchGroup.ALLOW_DETACH);
		proc1.setCapability(BranchGroup.ALLOW_DETACH);
		proc2.setCapability(BranchGroup.ALLOW_DETACH);
		proc3.setCapability(BranchGroup.ALLOW_DETACH);
		G1.setCapability(BranchGroup.ALLOW_DETACH);
		G2.setCapability(BranchGroup.ALLOW_DETACH);
		H1.setCapability(BranchGroup.ALLOW_DETACH);
		H2.setCapability(BranchGroup.ALLOW_DETACH);
		H3.setCapability(BranchGroup.ALLOW_DETACH);
		H4.setCapability(BranchGroup.ALLOW_DETACH);
		W1.setCapability(BranchGroup.ALLOW_DETACH);
		W2.setCapability(BranchGroup.ALLOW_DETACH);
		W3.setCapability(BranchGroup.ALLOW_DETACH);
		T1.setCapability(BranchGroup.ALLOW_DETACH);
		T2.setCapability(BranchGroup.ALLOW_DETACH);
		T3.setCapability(BranchGroup.ALLOW_DETACH);
		T4.setCapability(BranchGroup.ALLOW_DETACH);
		T5.setCapability(BranchGroup.ALLOW_DETACH);
		T6.setCapability(BranchGroup.ALLOW_DETACH);
		T7.setCapability(BranchGroup.ALLOW_DETACH);
		T8.setCapability(BranchGroup.ALLOW_DETACH);
		T9.setCapability(BranchGroup.ALLOW_DETACH);
		T10.setCapability(BranchGroup.ALLOW_DETACH);
		R1.setCapability(BranchGroup.ALLOW_DETACH);
		R2.setCapability(BranchGroup.ALLOW_DETACH);
		R3.setCapability(BranchGroup.ALLOW_DETACH);
		R4.setCapability(BranchGroup.ALLOW_DETACH);
		R5.setCapability(BranchGroup.ALLOW_DETACH);
		R6.setCapability(BranchGroup.ALLOW_DETACH);
		R7.setCapability(BranchGroup.ALLOW_DETACH);
		R8.setCapability(BranchGroup.ALLOW_DETACH);
		R9.setCapability(BranchGroup.ALLOW_DETACH);
		R10.setCapability(BranchGroup.ALLOW_DETACH);
		A1.setCapability(BranchGroup.ALLOW_DETACH);
		A2.setCapability(BranchGroup.ALLOW_DETACH);
		A3.setCapability(BranchGroup.ALLOW_DETACH);
		A4.setCapability(BranchGroup.ALLOW_DETACH);
		A5.setCapability(BranchGroup.ALLOW_DETACH);
		A6.setCapability(BranchGroup.ALLOW_DETACH);
		BG.setCapability(BranchGroup.ALLOW_DETACH);
		objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_READ);
		objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_WRITE);
		objRoot.setCapability(BranchGroup.ALLOW_DETACH);
		objRoot.setCapability(BranchGroup.ALLOW_CHILDREN_EXTEND);



		Transform3D translate = new Transform3D();
		translate.setScale(new Vector3d(1f,0.3f,1f));
		translate.set(new Vector3f(-0.5f, 0.25f, 0.0f));
		TransformGroup obj = new TransformGroup(translate);
		obj.addChild(squareplate());
		objRotate.addChild(obj);
		Transform3D translate1 = new Transform3D();
		translate1.set(new Vector3f(0.5f, 0.3f, 0.0f));
		TransformGroup obj1 = new TransformGroup(translate1);
		obj1.addChild(circularplate());
		objRotate.addChild(obj1);
		Transform3D translate2 = new Transform3D();
		translate.setScale(new Vector3d(0.5f,1.5f,1f));
		translate2.set(new Vector3f(-0.47f, -0.23f, 0.0f));
		TransformGroup obj2 = new TransformGroup(translate2);
		obj2.addChild(gauge());
		obj2.addChild(handg());
		//objRotate.addChild(createBox(new Vector3d(-0.5f,-0.2f,-0.1f), new Vector3d(0.2f,0.2f,0.01f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"gauge.jpg"));
		objRotate.addChild(obj2);
		Transform3D translate3 = new Transform3D();
		translate3.set(new Vector3f(0.5f, 0.2f, 0.0f));
		obj3 = new TransformGroup(translate3);
		obj3.addChild(BearingPlates(0));
		objRotate.addChild(obj3);

		Transform3D trans = new Transform3D();
		trans.set(new Vector3f(0.0f, 0.05f, -0.2f));
		TransformGroup ob = new TransformGroup(trans);
		ob.addChild(background());
		ob.addChild(Text());
		objRoot.addChild(objRotate);
		objRoot.addChild(ob);


		float[] knots = new float[2];
		knots[0] = 0;
		knots[1] = 1.0f;
		Quat4f[] quats = new Quat4f[2];
		Point3f[] positions = new Point3f[2];
		quats[0] = new Quat4f(0.0f, 0.0f, 0.0f, 0.0f);
		quats[1] = new Quat4f(0.0f, 0.0f, 0.0f, 0.0f);
		positions[0] = new Point3f(0,0.13f,0);
		positions[1] = new Point3f(0,0.11f,0);
		A1.addChild(Instance(knots,quats,positions,0.13f));
		positions[0] = new Point3f(0,0.11f,0);
		positions[1] = new Point3f(0,0.10f,0);
		A2.addChild(Instance(knots,quats,positions,0.11f));
		positions[0] = new Point3f(0,0.1f,0);
		positions[1] = new Point3f(0,0.09f,0);
		A3.addChild(Instance(knots,quats,positions,0.1f));

		positions[0] = new Point3f(0,0.09f,0);
		positions[1] = new Point3f(0,0.1f,0);
		A4.addChild(Instance(knots,quats,positions,0.09f));
		positions[0] = new Point3f(0,0.1f,0);
		positions[1] = new Point3f(0,0.11f,0);
		A5.addChild(Instance(knots,quats,positions,0.1f));
		positions[0] = new Point3f(0,0.11f,0);
		positions[1] = new Point3f(0,0.13f,0);
		A6.addChild(Instance(knots,quats,positions,0.11f));
		Transform3D trans0 = new Transform3D();
		trans0.set(new Vector3f(0.0f, 0.25f, 0.1f));
		TransformGroup ob0 = new TransformGroup(trans0);
		ob0.addChild(world());
		TransformGroup ob1 = new TransformGroup(trans0);
		ob1.addChild(bottomplate());
		Transform3D trans1 = new Transform3D();
		trans1.set(new Vector3f(0.0f, 0.5f, 0.1f));
		TransformGroup ob2 = new TransformGroup(trans1);
		ob2.addChild(world());
		TransformGroup ob3 = new TransformGroup(trans1);
		ob3.addChild(bottomplate());
		TransformGroup ob4 = new TransformGroup(trans1);
		ob4.addChild(worldtext());
		R9.addChild(ob2);
		R9.addChild(ob3);
		R9.addChild(ob4);
		R1.addChild(ob0);
		R2.addChild(ob1);
		//R3.addChild(worldtext());
		TransformGroup ob5 = new TransformGroup(trans0);
		ob5.addChild(BearingPlates(1));
		TransformGroup ob6 = new TransformGroup(trans0);
		ob6.addChild(bptext());
		TransformGroup ob7 = new TransformGroup(trans0);
		ob7.addChild(DatumGauge());
		TransformGroup ob8 = new TransformGroup(trans0);
		ob8.addChild(Platform());
		TransformGroup ob9 = new TransformGroup(trans0);
		ob9.addChild(threeplates(0.0f));
		Transform3D translai = new Transform3D();
		translai.set(new Vector3f(-0.9f, 0.4f, 0.1f));
		TransformGroup oi = new TransformGroup(translai);
		Text2D ti = new Text2D("Loading Criterion : Load is increased in increments of 25% of the ultimate load carrying", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		oi.addChild(ti);
		I1.addChild(oi);
		Transform3D translai1 = new Transform3D();
		translai1.set(new Vector3f(-0.9f, 0.35f, 0.1f));
		TransformGroup oi1 = new TransformGroup(translai1);
		Text2D ti1 = new Text2D("until it reaches the ultimate load intensity or the total settlement of the plate is 25 mm", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		oi1.addChild(ti1);
		I1.addChild(oi1);
		Transform3D translai2 = new Transform3D();
		translai2.set(new Vector3f(-0.9f, 0.3f, 0.1f));
		TransformGroup oi2 = new TransformGroup(translai2);
		Text2D ti2 = new Text2D("or the soil under the plate fails whichever occurs earlier.", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		oi2.addChild(ti2);
		I1.addChild(oi2);
		Transform3D translai3 = new Transform3D();
		translai3.set(new Vector3f(-0.9f, 0.4f, 0.1f));
		TransformGroup oi3 = new TransformGroup(translai3);
		Text2D ti3 = new Text2D("Entire load is removed quickly but gradually and the plate is allowed to rebound when no ", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		oi3.addChild(ti3);
		I2.addChild(oi3);
		Transform3D translai4 = new Transform3D();
		translai4.set(new Vector3f(-0.9f, 0.35f, 0.1f));
		TransformGroup oi4 = new TransformGroup(translai4);
		Text2D ti4 = new Text2D("further rebound occurs or the rate of rebound becomes negligible the reading of the dial gauges ", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		oi4.addChild(ti4);
		I2.addChild(oi4);
		Transform3D translai5 = new Transform3D();
		translai5.set(new Vector3f(-0.9f, 0.3f, 0.1f));
		TransformGroup oi5 = new TransformGroup(translai5);
		Text2D ti5 = new Text2D("are again noted.", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		oi5.addChild(ti5);
		I2.addChild(oi5);
		Transform3D ansla3 = new Transform3D();
		ansla3.set(new Vector3f(-0.9f, 0.45f, 0.1f));
		TransformGroup eoo = new TransformGroup(ansla3);
		Text2D ee0 = new Text2D("Click Unloading To Remove Weight", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 10, Font.BOLD);	
		eoo.addChild(ee0);
		proc3.addChild(eoo);
		Transform3D ransla3 = new Transform3D();
		ransla3.set(new Vector3f(-0.9f, 0.45f, 0.1f));
		TransformGroup oo = new TransformGroup(ransla3);
		Text2D e0 = new Text2D("Click Add 25% Weight To Add 25% Weight Of Previous Weight", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 10, Font.BOLD);	
		oo.addChild(e0);
		proc2.addChild(oo);
		Transform3D transla3 = new Transform3D();
		transla3.set(new Vector3f(-0.9f, 0.45f, 0.1f));
		o3 = new TransformGroup(transla3);
		Text2D te0 = new Text2D("", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		o3.addChild(te0);
		clicknext.addChild(o3);
		Transform3D transla0 = new Transform3D();
		transla0.set(new Vector3f(-0.9f, 0.4f, 0.1f));
		TransformGroup o0 = new TransformGroup(transla0);
		Text2D t0 = new Text2D("1) Foundation Pit shall be excavated up to a level of a foundation of building or other", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		o0.addChild(t0);
		R2.addChild(o0);
		Transform3D transla1 = new Transform3D();
		transla1.set(new Vector3f(-0.9f, 0.35f, 0.1f));
		TransformGroup o1 = new TransformGroup(transla1);
		Text2D t1 = new Text2D("structure, to be constructed.", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		o1.addChild(t1);
		R2.addChild(o1);
		Transform3D transla2 = new Transform3D();
		transla2.set(new Vector3f(-0.9f, 0.3f, 0.1f));
		TransformGroup o2 = new TransformGroup(transla2);
		Text2D t2 = new Text2D("2) Test plate of given size shall be placed on the prepared sub-base.", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		o2.addChild(t2);
		R2.addChild(o2);
		Transform3D transla4 = new Transform3D();
		transla4.set(new Vector3f(-0.9f, 0.4f, 0.1f));
		TransformGroup o4 = new TransformGroup(transla4);
		Text2D t4 = new Text2D("1) Dial gauges shall be provided to note the settlements with least accuracy count of 0.01mm.", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		o4.addChild(t4);
		R5.addChild(o4);
		Transform3D transla5 = new Transform3D();
		transla5.set(new Vector3f(-0.9f, 0.35f, 0.1f));
		TransformGroup o5 = new TransformGroup(transla5);
		Text2D t5 = new Text2D("2) Bearing plate is placed on the test plate over which hydraulic jack is located.", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		o5.addChild(t5);
		R5.addChild(o5);
		Transform3D transla6 = new Transform3D();
		transla6.set(new Vector3f(-0.9f, 0.3f, 0.1f));
		TransformGroup o6 = new TransformGroup(transla6);
		Text2D t6 = new Text2D("3) Another bearing plate is placed over hydraulic jack which act as load transferring media.", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		o6.addChild(t6);
		R5.addChild(o6);
		Transform3D transla7 = new Transform3D();
		transla7.set(new Vector3f(-0.9f, 0.4f, 0.1f));
		TransformGroup o7 = new TransformGroup(transla7);
		Text2D t7 = new Text2D("The system is loaded with point load pattern so that the net load in punching is applied on", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		o7.addChild(t7);
		proc1.addChild(o7);
		Transform3D transla8 = new Transform3D();
		transla8.set(new Vector3f(-0.9f, 0.35f, 0.1f));
		TransformGroup o8 = new TransformGroup(transla8);
		Text2D t8 = new Text2D("to the plate without any eccentricity. Point load can be a loaded excavator or loaded lorry ", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		o8.addChild(t8);
		proc1.addChild(o8);
		Transform3D transla9 = new Transform3D();
		transla9.set(new Vector3f(-0.9f, 0.3f, 0.1f));
		TransformGroup o9 = new TransformGroup(transla9);
		Text2D t9 = new Text2D("or Kent ledgesystem,.etc.", new Color3f(0.0f, 0.0f, 0.0f),"Courier", 9, Font.BOLD);	
		o9.addChild(t9);
		proc1.addChild(o9);
		R4.addChild(ob5);
		R5.addChild(ob6);
		R6.addChild(ob7);
		R7.addChild(ob8);
		R8.addChild(ob9);
		H1.addChild(hand(0.0f));
		H2.addChild(hand(4.0f));
		H3.addChild(hand(3.0f));
		H4.addChild(hand(2.0f));
		H1.addChild(handp(0.0f));
		H2.addChild(handp(4.0f));
		H3.addChild(handp(3.0f));
		H4.addChild(handp(2.0f));
		W3.addChild(weight(0.02f,0.4f));
		W2.addChild(weight(0.12f,0.1f));
		W1.addChild(weight(0.22f,0.025f));
		G1.addChild(createBox(new Vector3d(0.0f,-0.2f,0.32f), new Vector3d(1.5f,0.6f,0.2f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"graphload.jpg"));
		G1.addChild(createBox(new Vector3d(0.0f,-0.2f,0.31f), new Vector3d(1.5f,2f,0.1f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"white.jpg"));
		G2.addChild(createBox(new Vector3d(0.0f,-0.2f,0.32f), new Vector3d(1.5f,0.6f,0.2f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"graphunload.jpg"));
		G2.addChild(createBox(new Vector3d(0.0f,-0.2f,0.31f), new Vector3d(1.5f,2f,0.1f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"white.jpg"));
		Result1.addChild(createBox(new Vector3d(0.0f,-0.18f,0.32f), new Vector3d(1.2f,0.6f,0.11f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"R2.png"));
		Result1.addChild(createBox(new Vector3d(0.0f,-0.1f,0.30f), new Vector3d(1.5f,2f,0.1f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"white.jpg"));
		Result2.addChild(createBox(new Vector3d(0.3f,-0.18f,0.32f), new Vector3d(1.2f,0.6f,0.11f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"R4.png"));
		Result2.addChild(createBox(new Vector3d(0.0f,-0.1f,0.30f), new Vector3d(1.5f,2f,0.1f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"white.jpg"));
		Result4.addChild(createBox(new Vector3d(0.0f,-0.18f,0.32f), new Vector3d(1.2f,0.6f,0.11f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"R3.png"));
		Result3.addChild(createBox(new Vector3d(0.0f,-0.1f,0.30f), new Vector3d(1.5f,2f,0.1f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"white.jpg"));
		Result3.addChild(createBox(new Vector3d(0.18f,-0.18f,0.32f), new Vector3d(1.2f,0.6f,0.11f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"R1.png"));
		Result4.addChild(createBox(new Vector3d(0.0f,-0.1f,0.30f), new Vector3d(1.5f,2f,0.1f),new Vector3d(0,0,0),new Color3f(1f, 1f, 0.9f),"white.jpg"));
		panel1.add(next);
		panel1.add(wnext);
		panel1.add(rnext);
		panel1.add(label);
		list.addItem("Cohesive Soil");
		list.addItem("Non-Cohesive Soil");
		panel1.add(list);
		panel1.add(restart);
		getContentPane().add(panel1, "North");
		BG.addChild(backg());
		//sceen.addChild(objRoot);
		sceen.addChild(BG);
		sceen.addChild(R1);
		sceen.addChild(R2);
		restart.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0){
				count =0;
				r = 0;
				w = 0;
				sceen.removeAllChildren();
				sceen.addChild(R1);
				sceen.addChild(R2);
				sceen.addChild(BG);
				}
				});
		list.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0){
				String cmbType = (String) list.getSelectedItem();
				if(count == 0){
				if(cmbType == "Non-Cohesive Soil"){
				soil = 1;
				}
				else	
				soil = 0;
				}
				//System.out.println(soil);
				}
				});
		wnext.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0){

				if( w == 100){
				sceen.removeChild(H2);
				sceen.removeChild(I1);
				sceen.addChild(H3);		
				sceen.removeChild(A1);
				sceen.addChild(A2);
				sceen.addChild(W2);
				w++;

				}
				else if(w == 101){
				sceen.removeChild(A2);
				sceen.removeChild(H3);
				sceen.addChild(H4);
				sceen.addChild(A3);
				sceen.addChild(W1);
				sceen.removeChild(proc2);
				//sceen.addChild(clicknext);
				count = 200;
				w++;
				}

				}
		});
		rnext.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0){

				if( r == 100){
				sceen.removeChild(H4);
				sceen.removeChild(I2);
				sceen.addChild(H3);	
				sceen.removeChild(A3);
				sceen.removeChild(W1);
				sceen.addChild(A4);
				r++;

				}
				else if(r == 101){
				sceen.removeChild(H3);
				sceen.addChild(H2);	
				sceen.removeChild(A4);
				sceen.removeChild(W2);
				sceen.addChild(A5);
				r++;
				}
				else if(r == 102){
					sceen.removeChild(proc3);
					//sceen.addChild(clicknext);
					sceen.removeChild(H2);
					sceen.addChild(H1);	
					sceen.removeChild(A5);
					sceen.removeChild(W3);
					sceen.addChild(A6);
					count = 500;
					r++;
				}

				}
		});
		next.addActionListener(new ActionListener() {

				public void actionPerformed(ActionEvent arg0){
				if(count == 0){
				sceen.removeChild(R1);
				sceen.removeChild(R2);
				sceen.addChild(R9);

				}
				else if(count == 1){
				sceen.removeChild(R9);
				sceen.addChild(R1);
				sceen.addChild(R4);
				sceen.addChild(R8);
				sceen.addChild(R5);
				sceen.addChild(R6);
				}
				else if(count == 2){
					sceen.addChild(proc1);
					sceen.removeChild(R5);
					sceen.removeChild(R6);
					sceen.addChild(R7);
					sceen.addChild(H1);
				}
				else if(count == 3){
					sceen.removeChild(H1);
					sceen.addChild(I1);
					//	sceen.removeChild(clicknext);
					sceen.removeChild(proc1);
					sceen.addChild(proc2);
					sceen.addChild(H2);
					sceen.removeChild(R8);
					sceen.addChild(W3);
					sceen.addChild(A1);
					w = 100;

				}
				else if(count == 200){
					sceen.addChild(G1);
				} 
				else if(count == 201){
					sceen.removeChild(G1);
					//	sceen.removeChild(clicknext);
					sceen.addChild(proc3);
					sceen.addChild(I2);
					r = 100;
				}
				else if(count == 500){
					sceen.addChild(G2);
				}		
				else if(count == 501){
					sceen.removeChild(G2);
					Random random = new Random();//to generate random variable
					int x=random.nextInt(2);
					if(soil == 0){
						if(x == 1){
							sceen.addChild(Result1);
						}
						else{	
							sceen.addChild(Result2);
						}
					}
					else{
						if(x == 1){
							sceen.addChild(Result3);
						}
						else{	
							sceen.addChild(Result4);
						}

					}
				}
				else if(count == 502){
					if(soil == 0)
						test(1);
					else
						test(0);
					count = count - 1;
				}
				count = count + 1;

				}
		});

		Transform3D translate7 = new Transform3D();
		translate7.set(new Vector3f(0.0f, 0.07f, -0.05f));
		translate7.rotX(-Math.PI/10.0f);
		TransformGroup obj7 = new TransformGroup(translate7);
		obj7.addChild(sceen);
		BranchGroup temp = new BranchGroup();
		temp.addChild(obj7);
		temp.compile();
		return temp;


	}




	public Object(){
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		add ("Center", canvas3D);
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		BranchGroup scene = createSceneGraph_def();
		content = new BranchGroup();
		content.setCapability(17);
		simpleU.getViewingPlatform().setNominalViewingTransform();
		simpleU.addBranchGraph(scene);
	}
}

public class Experiment extends JApplet{
	public static Object setup;

	public static void main(String args[] ){

		//Frame frame1 = new MainFrame(new Object(),800,800);
		setup = new Object();
		setup.setSize(800, 800);
		setup.setVisible(true);
		setup.setDefaultCloseOperation(3);


	}

}
