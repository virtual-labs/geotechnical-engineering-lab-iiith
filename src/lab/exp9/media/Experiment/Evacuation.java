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

/**The class BuildingApp is used for GUI part. It is used for showing building, people, obstacles etc.
 *
 */
class BuildingApp extends Applet {
	/**  function does: createAppearance function defines the colors and design part of floors, doors, etc.
	 *    parameter    : it takes flag as parameter which is used for specifying stairs, doors and floors
	 *                      flag has values 2 and other than 2. 2 is for doors and stairs and the others are for floors.
	 *    return values: this function returns appear which sets material object to specified object.
	 */
	public Appearance createAppearance(int flag){

		Appearance twistAppear = new Appearance();                                 // appear sets the material object to specified object
		
		if (flag == 0)
		{
			Appearance appear = new Appearance();
			Material material = new Material();                                // material sets this materials's diffuse color 
			material.setDiffuseColor(0.00f, 0.3f, 0.0f);
			appear.setMaterial(material);
			return appear;
		}
		else if(flag == 1)
		{
			Appearance appear = new Appearance();
			Material material = new Material();                                // material sets this materials's diffuse color 
			material.setDiffuseColor(0.005f, 0.1f, 0.1f);
			appear.setMaterial(material);
			return appear;
		}
		else if(flag == 2)
		{
			Appearance appear = new Appearance();
			Material material = new Material();                                // material sets this materials's diffuse color 
			material.setDiffuseColor(1.0f, 1.0f, 1.0f);
			appear.setMaterial(material);
			return appear;
		}
		else if(flag == 5){
			Appearance appear = new Appearance();
			Material material = new Material();                                // material sets this materials's diffuse color 
			material.setDiffuseColor(0.3f,0.8f, 0.0f);
			appear.setMaterial(material);
			return appear;
		}
		else{
			Appearance appear = new Appearance();
			Material material = new Material();
			material.setDiffuseColor(0.002f, 0.0f, 0.001f);                        // material sets this materials's diffuse color 
			appear.setMaterial(material);
			return appear;
		}

	}

	/** createScene function : is used for making persons which are represented by spheres with lines on top and bottom
	 *  returns scene which is of type BranchGroup and is used for making analogue of persons
	 */ 
	BranchGroup createScene(int size) {
		BranchGroup scene = new BranchGroup();                                    // scene is the final object which is returned--it represents persons
		TransformGroup SphereTG = new TransformGroup();                           // it is used to make structures of persons which are finally included in scene
		float rad = 0.03f;
		if(size >  8 && size <= 20)
			rad = 0.02f;

		if(size > 20)
		       rad = 0.01f;

		Sphere sp = new Sphere(rad,createAppearance(5));                                            // sp is an instance of sphere
		Box box = new Box(0.001f, 0.05f, 0.001f, createAppearance(1));            // box represents the small line which alongwith sphere represents a person
		SphereTG.addChild(sp);
		SphereTG.addChild(box);
		scene.addChild(SphereTG);
		AmbientLight lightA = new AmbientLight();
		lightA.setInfluencingBounds(new BoundingSphere());
		scene.addChild(lightA);

		DirectionalLight lightD1 = new DirectionalLight();
		lightD1.setInfluencingBounds(new BoundingSphere());
		Vector3f direction1 = new Vector3f(-1.0f, -1.0f, -0.5f);
		direction1.normalize();
		lightD1.setDirection(direction1);
		lightD1.setColor(new Color3f(0.5f, 0.1f, 0.3f));
		scene.addChild(lightD1);

		DirectionalLight lightD2 = new DirectionalLight();
		lightD2.setInfluencingBounds(new BoundingSphere());
		Vector3f direction2 = new Vector3f(1.0f, -1.0f, -0.5f);
		direction2.normalize();
		lightD2.setDirection(direction2);
		lightD2.setColor(new Color3f(0.5f, 0.1f, 0.3f));
		scene.addChild(lightD2);

		Background bg = new Background();                                          // bg represents background
		bg.setColor(1.0f, 1.0f, 1.0f);
		bg.setApplicationBounds(new BoundingSphere());
		scene.addChild(bg);

		return scene;
	}

	/** Function obstacle : it makes obstacles
	 *   parameters  :  input are x:x coordinate, z: z coordinate, k: floor no , tag: it works as a flag and according to its values door, stair or obstacle are chosen
	 *   returns     :  it returns obstacle 
	 */ 
	BranchGroup obstacle(float xpos,float zpos,int floor,int tag,int size){
		int temp = floor;                                                                // temp = floor number just to save floor value
		if(tag == 0)
			temp = 0;

		BranchGroup obstacleBG = new BranchGroup();                                  // obstacleBG is the obstacle which is returned
		Appearance appearance = createAppearance(0);                              // appearance is for defining floor's color,design etc.
		Transform3D translate = new Transform3D();                                // translate is used for shifting axes to make objects which are not at origin
		translate.set(new Vector3f(xpos, -0.25f + temp*0.53f,zpos));
		TransformGroup obstacleTG = new TransformGroup(translate);
		float x , y ,z;
		x = 0.06f;
		y = 0.03f;
		z = 0.06f;

		if(size >8){
			x=z=0.04f;
		}
		else if(size > 16){
			x=z=0.02f;
		}
		else if (size > 25){
			x =z=0.01f;
		}
		Box box = new Box(x, y, z, createAppearance1());              // box represents obstacle 
		Box box1 = new Box(0.05f, 0.08f, 0.05f, createAppearance(1));             // box1 represents doors 
		Box box2 = new Box(0.001f, 0.5f, 0.001f, createAppearance(0));            // box2 represents stairs

		if (tag == 1){
			obstacleTG.addChild(box);
		}else if ( tag == 0 && floor == 0){
			obstacleTG.addChild(box1);
		}else if( tag == 0 && floor == 1){
			obstacleTG.addChild(box2);
		}
		obstacleBG.addChild(obstacleTG);
		obstacleBG.compile();
		return obstacleBG;                                                          
	}

	/** Floor is used for making floors
	 */
	public class Floor {

		private BranchGroup floorBG;

		public Floor(float distance) {

			floorBG = new BranchGroup();
			Appearance appearance = createAppearance(0);                       // 0 passed in createAppearance refers to floors.
			Transform3D translate = new Transform3D();                         // translate is used for shifting axes to make objects which are not at origin
			translate.set(new Vector3f(0.0f, distance + 0.2f, 0.0f));
			TransformGroup floorTG = new TransformGroup(translate);
			Box box = new Box(0.6f, 0.02f, 0.6f, appearance);                  // box is used for making boxes

			floorTG.addChild(box);
			floorBG.addChild(floorTG);
			floorBG.compile();

		}

		public BranchGroup getBG(){
			return floorBG;                                                   // returning the group floor
		}

	}

	/** This function makes path of a person from his position to door
	 * 
	 */
	public BranchGroup Instance(float[] knots,Quat4f[] quats,Point3f[] positions,int size,float speed){
		
			//			pnoOfDoors  = Integer.parseInt(noOfDoors.getText());
		float movement = 100000*speed;
		int temps  = (int)(movement);
		BranchGroup objRoot = new BranchGroup();                      // Create the root of the branch graph
		Alpha alpha = new Alpha(1, temps);                           // alpha is for moving the point from initial to final position
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
		target.addChild(createScene(size));

		Background background = new Background();
		background.setColor(1.0f, 1.0f, 1.0f);
		background.setApplicationBounds(new BoundingSphere());
		objRoot.addChild(background);


		objRoot.compile();

		return objRoot;
	} 

	/** This class is used to make pillars
	 */
	public class Pillar {
		private BranchGroup pillarBG;

		/** The function pillar makes pillars. 
		 *   parameters: x coordinate and z coordinate
		 */
		public Pillar(float Xposition, float Zposition) {
			pillarBG = new BranchGroup();
			Appearance appearance = createAppearance(3);                      // appearance is used to give appearance(color, design etc.) to pillar
			Transform3D translate = new Transform3D();                         
			translate.set(new Vector3f(Xposition, 0.0f + 0.2f, Zposition));   // translate is used for shifting axes to make objects which are not at origin
			TransformGroup pillarTG = new TransformGroup(translate);
			Box box = new Box(0.02f, 0.5f, 0.02f, appearance);                 // box is used for making boxes
			pillarTG.addChild(box);
			//objRotate.addChild(pillarTG);
			pillarBG.addChild(pillarTG);
			pillarBG.compile();
		}


		public BranchGroup getBG() {
			return pillarBG;
		}
	}

	/** the function createappearance returns an object appear which has the properties : shininess, color etc.
	 */
	Appearance createAppearance1() {

		Appearance appear = new Appearance();
		Material material = new Material();
		Random rand = new Random();
		material.setDiffuseColor(0.2f, 0.1f, 0.3f);
		material.setShininess(50.0f);
		appear.setMaterial(material);
		return appear;
	}

	/** The following function combines pillars, floors and other objects of building
	 *    parameters : 
	 *    returns    :
	 *
	 *
	 */ 										        
	public BranchGroup createSceneGraph_def(SimpleUniverse simpleU,int[][][] grid , 
			int[] row , int[] col ,int floors,int[][] position , int time,
			int numbering,int[][] floorofn,int[] noOfDoors , int[][] door,float speed){

		BranchGroup objRoot = new BranchGroup();
		TransformGroup objRotate = new TransformGroup();

		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_WRITE);
		objRotate.setCapability(TransformGroup.ALLOW_TRANSFORM_READ);
		objRoot.addChild(objRotate);

		objRotate.addChild(new Floor(0.5f).getBG());
		objRotate.addChild(new Floor(-0.5f).getBG());

		objRotate.addChild(new Pillar(0.58f, 0.58f).pillarBG);
		objRotate.addChild(new Pillar(-0.58f, -0.58f).pillarBG);
		objRotate.addChild(new Pillar(0.58f, -0.58f).pillarBG);
		objRotate.addChild(new Pillar(-0.58f, 0.58f).pillarBG);
		/*loop counters */
		int i = 0;
		int j;
		int  k;

		int temp;//storing temp variable
		float kn = 0; // 1/time = the width of interval
		float x;//temp variable to store the x co-ordinate
		float y;//temp variable to store y co-ordinate
		float px = 0;//storing the  value to shift x-axis 
		float pz = 0;//storing the value to shift z-axis
		float temp1 = 0;//save the temporary value

		for (k=0; k<2; k++){
			for (i=0; i<noOfDoors[k]; i++){
				temp1   =  -1*0.3f + 0.5f/col[k];
				j    =  door[k][i]%col[k];
				temp =  door[k][i]/col[k];
				px   =  j*(1.0f/(float)col[k]) + temp1;
				pz   =  temp*(1.0f/(float)col[k])  + temp1;
				objRotate.addChild(obstacle(px - 0.15f ,pz - 0.10f , k,0,row[0]));
			}
		}
		for(k=0;k<2;k++){
			for(i=0;i<row[k];i++){
				for(j=0;j<col[k];j++){
					if(grid[k][i][j] == 1){
						temp1 = -1*0.3f + 0.5f/col[k];
						px = j*(1.0f/(float)col[k])  + temp1 ;
						pz = i*(1.0f/(float)col[k])  + temp1;
						objRotate.addChild(obstacle(px - 0.15f,pz - 0.10f,k,1,row[0]));
					}
				}
			}
		}
		for (i=2; i<numbering; i++)
		{
			float[] knots = new float[time];// = {0.0f,0.3f,0.6f,1.0f};
			knots[time-1] = 1.0f;
			kn = 1.0f/time;

			for (k=1; k<time - 1 ; k++){
				knots[k] = knots[k-1] + kn;
			}

			knots[0] = 0.0f;	
			temp = time - 1;
			Quat4f[] quats = new Quat4f[temp+1];
			Point3f[] positions = new Point3f[temp+1];

			for (k=0; k<time; k++){
				x = position[i][k]/col[0];
				y = position[i][k]%col[0];
				temp1 = -1*0.3f + 0.5f/col[floorofn[i][k]];
				px = y*(1.0f/(float)col[floorofn[i][k]])  + temp1 ;
				pz = x*(1.0f/(float)col[floorofn[i][k]])  + temp1;
				positions[k] = new Point3f(px - 0.15f,-0.25f + floorofn[i][k]*0.55f 
						- floorofn[i][k]*0.05f,pz - 0.10f );
				quats[k] = new Quat4f(0.0f, 0.0f, 0.0f, 0.0f);
			}       										
			objRotate.addChild(Instance(knots,quats,positions,row[0],speed));
		}

		MouseRotate myMouseRotate = new MouseRotate();
		myMouseRotate.setTransformGroup(objRotate);
		myMouseRotate.setSchedulingBounds(new BoundingSphere());
		objRoot.addChild(myMouseRotate);

		MouseWheelZoom myZoom = new MouseWheelZoom();
		myZoom.setTransformGroup(objRotate);
		myZoom.setSchedulingBounds(new BoundingSphere());
		objRoot.addChild(myZoom);

		objRoot.compile();
		return objRoot;
	}


	public BuildingApp(int[][][] grid , int[] row , int[] col,int floors,
			int[][] position , int time,int numbering,int[][] floorofn,
			int[] noOfDoors,int[][] door,float speed) {
		setLayout(new BorderLayout());
		GraphicsConfiguration config = SimpleUniverse.getPreferredConfiguration();
		Canvas3D canvas3D = new Canvas3D(config);
		add ("Center", canvas3D);
		SimpleUniverse simpleU = new SimpleUniverse(canvas3D);
		BranchGroup scene = createSceneGraph_def(simpleU,grid,row,col,floors,
				position,time,numbering,floorofn,noOfDoors,door,speed);
		simpleU.getViewingPlatform().setNominalViewingTransform();
		simpleU.addBranchGraph(scene);
	}
}
public class Evacuation extends JApplet{

	static int floors; // No of Floors
	static int[]  noOfDoor= new int[10];	// No Of Door in each floor
	static int[] stepmark = new int[1000]; // Mark The Previos step Of agent
	static int[] row = new int[10]; // No of rows in the grid
	static int[] col = new int[10]; // No of col in the grid
	static int[][] next = new int[10000][1000];//stor the step of nth agent at time t
	static int[][] floorofn = new int[1000][1000];//current floor of agent
	static int[][] personfromdoor = new int[10][1000];//number of person evacaute from door
	static int[][] countofpos  = new int[10][10000];//count the pos of of path saved currently
	static int[][] door = new int[10][1000];//doors  position in each floor
	static int[][] flag = new int[10][1000];//it mark wether the person moves in the time t or have to wait
	static int[][] person = new int[10][1000];//it stores the person currently mapped to the door till time t
	static int[][] density = new int[10][1000];//it stores the density of people between agent and door
	static int[][][] graph = new int[10][1000][1000];//it generates a graph from grid
	static int[][][] grid = new int[10][1000][1000];//take input
	static int[][][] position= new int[10][1000][1000];//it store the position of person from initial pos to door
	static int[][][] stairend = new int[10][10][100];//it stores the end of stairs
	static 	JTextField width = new JTextField(20);//text field to get number of rows of floor
	static	JTextField length = new JTextField(20);//text field to get number of cols of floor
	static	JTextField noOfPeoples = new JTextField(20);//text field to get number of peoples
	static	JTextField noOfObstacles = new JTextField(20);//text field to get number of obstacles
	static	JTextField noOfDoors = new JTextField(20);//text field to get number of doors
	static	JLabel label_Width = new JLabel("Width of the Hall ");//label of textfield width
	static	JLabel label_Length = new JLabel("Length of the Hall ");//label of textfield length
	static	JLabel label_NoOfPeoples = new JLabel("Total Number Of Persons ");//label of textfield noofpeoples
	static	JLabel label_NoOfObstacles = new JLabel("Total Number Of Obstacles ");//label of textfield noofobstacles
	static	JLabel label_NoOfDoors = new JLabel("Total Number Of Doors");//label of textfield noofdoors
	static	JTextField speedOfPerson = new JTextField(20);//text field to get number of doors
	static	JLabel label_speedOfPerson = new JLabel("Speed Of Person (btw 0 to 1) ");//label of textfield width

	static	Font font = new Font("Serif", Font.BOLD, 16);//setting fonts of input panel
	static	JPanel panel = new JPanel();//creating panel
	static	JButton ok = new JButton("OK");//creating ok button
	static  int pwidth;//temp variable to take value of width value from panel textfield width
	static  int plength;//temp variable to take value of length value from panel textfield length
	static  int pnoOfPeoples;//temp variable to take value of noofpeoples value from panel textfield noofpeoples
	static  int pnoOfObstacles;//temp variable to take value of noofobstacles value from panel textfield noofobstacles
	static  int pnoOfDoors;//temp variable to take value of noofdoors value from panel textfield noofdoors
	static	String file;//input file
	static float speed = 0.5f;

	static	int mode = 0;//mode wether jpanel input taken or not

	public static void main(String args[])throws InterruptedException, Exception {

		/*loop Counters */

		int i ;
		int j ;
		int k ;
		int p ;
		int q ;

		file = "input";
		String strSpeed;

		try{
			String[] choice = {"Input Through File", "Randomly Generated File"};//give choice to user so he can give file in the given format or can generate a input file
			String Input = (String) JOptionPane.showInputDialog(null, "Choose one"
					, "BuildingApp", JOptionPane.QUESTION_MESSAGE, null, choice, choice[0]);//taking the input file 

			if(Input.equals("Input Through File")){
				mode = 1;
				file=JOptionPane.showInputDialog(null
						,"Enter file name(should be in same dir as this app)");
				strSpeed = JOptionPane.showInputDialog(null,"Enter The Speed Of A Person( btwn 0 to 1");
				speed = Float.parseFloat(strSpeed);


			}else{     										

				JFrame frame = new JFrame("Input panel");//creating the panel
				frame.setSize(250, 430);
				Container myContainer = frame.getContentPane();//creating the conatiner
				myContainer.setLayout(new FlowLayout());

				label_Width.setFont(font);
				myContainer.add(label_Width, BorderLayout.CENTER);
				myContainer.add(width);

				label_Length.setFont(font);
				myContainer.add(label_Length, BorderLayout.CENTER);
				myContainer.add(length);

				label_NoOfPeoples.setFont(font);
				myContainer.add(label_NoOfPeoples, BorderLayout.CENTER);
				myContainer.add(noOfPeoples);

				label_NoOfObstacles.setFont(font);
				myContainer.add(label_NoOfObstacles, BorderLayout.CENTER);
				myContainer.add(noOfObstacles);

				label_NoOfDoors.setFont(font);
				myContainer.add(label_NoOfDoors, BorderLayout.CENTER);
				myContainer.add(noOfDoors);
				
				label_NoOfDoors.setFont(font);
				myContainer.add(label_speedOfPerson, BorderLayout.CENTER);
				myContainer.add(speedOfPerson);

				panel.add(ok);
				myContainer.add(panel);

				ok.addActionListener(new ActionListener() {					//event when pressing ok button
						public void actionPerformed(ActionEvent evt) {
						try {
						pwidth  = Integer.parseInt(width.getText());
						plength  = Integer.parseInt(length.getText());
						pnoOfPeoples  = Integer.parseInt(noOfPeoples.getText());
						pnoOfObstacles  = Integer.parseInt(noOfObstacles.getText());
						pnoOfDoors  = Integer.parseInt(noOfDoors.getText());
						speed  = Float.parseFloat(speedOfPerson.getText());

						int [][]array=new int[pwidth][plength];//array to create a grid in input file
						int x;//temp x to indicate x-co-ordinate which generated randomly
						int y;//temp y to indicate y-co-ordinate which generated randomly
						int i=0;//loop counter
						int j=0;//loop counter

						for(i=0;i<pwidth;i++){

							for(j=0;j<plength;j++){
								array[i][j] =0;
							}
						}
				
						for(i=0;i<pnoOfPeoples;i++){

							Random randomx = new Random();//to generate random variable
							Random randomy = new Random();//to generate random variable
							x=randomx.nextInt(pwidth);
							y=randomy.nextInt(plength);
							
							if(array[x][y] == 0)
								array[x][y] = 2;
							else
								i--;
						}

						for(i=0;i<pnoOfObstacles;i++){

							Random randomx = new Random();//to generate random variable
							Random randomy = new Random();//to generate random variable
							x = randomx.nextInt(pwidth);
							y = randomy.nextInt(plength);

							if(array[x][y] == 0)
								array[x][y] = 1;
							else 
								i--;
						}

						String data = pwidth+" "+plength+"\n";//to write the string in file 

						for(i=0;i<pwidth;i++){

							for(j=0;j<plength;j++){

								if(j != plength-1)
									data = data + ""+array[i][j]+" ";
								else
									data = data + ""+array[i][j]+"\n";
							}
						}

						data = data + ""+pnoOfDoors+"\n";

						for(i=0;i<pnoOfDoors;i++){

							Random randomx= new Random();//to generate random variable
							Random randomy= new Random();//to generate random variable

							x = randomx.nextInt(pwidth);
							y = randomy.nextInt(plength);
							if(array[x][y] == 0)	
								data = data + ""+x+" "+y+"\n";
							else
								i--;

						}

						file = "input";
						mode = 1;	

						try{
							byte buff[] = data.getBytes();

							FileOutputStream f = new FileOutputStream("input");
							try{
								f.write(buff);

								f.close();  
							}catch (IOException e) {}
	
						}catch (FileNotFoundException e) {}

						//	System.out.println(pwidth + " " + plength + " " + pnoOfPeoples + " " + pnoOfObstacles + " " + pnoOfDoors );
					} catch (NumberFormatException e) {
							JOptionPane.showMessageDialog(null, "The Input should numbers and number of floors should be an integer");
						}
					}	
				});
				frame.setResizable(false);
				frame.setVisible(true);
				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


			}

			int numbering = 2; //number the agents
			byte arrin[] = new byte[100000];//array to take input from file

			while(mode == 0)
			{

				System.out.print("");
			}

			if(mode == 1){

			System.out.println(file);	

			FileInputStream fin = new FileInputStream(file);	



			int n1 = fin.available();


			fin.read(arrin);

			fin.close();
			}

			int tok = 0; //index of file pointer
			String str = new String(arrin);
			String delims = "[ ' ' \n]";

			String[] tokens = str.split(delims);
			floors = 1;
			for (i=0; i<floors; i++){
				String s0 = tokens[tok];
				String s1 = tokens[tok+1];
				tok = tok + 2;
				row[i] = Integer.parseInt(s0); 
				col[i] = Integer.parseInt(s1);

				for (j=0; j<row[i]; j++){
					for(k=0;k<col[i];k++){

						grid[i][j][k]=Integer.parseInt(tokens[tok]);
						if( grid[i][j][k] == 2){
							grid[i][j][k] = numbering++;
							next[numbering-1][0] = j*col[i] + k;
						}
						tok++ ;
					}
				}
				noOfDoor[i] = Integer.parseInt(tokens[tok]);
				tok++ ;
				for (j=0; j<noOfDoor[i]; j++){

					personfromdoor[i][j] = 0;
					door[i][j] = (Integer.parseInt(tokens[tok]))*col[i] 
						+ (Integer.parseInt(tokens[tok + 1]));
					tok = tok + 2;
					if(i > 0 )
					{
						stairend[i][j][0] = (Integer.parseInt(tokens[tok])*col[i-1]) 
							+ (Integer.parseInt(tokens[tok+1]));
						tok = tok + 2;
					}
				}
			}

			for(i=0; i<numbering; i++){
				for (j=1; j<100; j++){
					next[i][j] = 0;
				}
			}
			for (k=0; k<floors; k++){
				for (i=0; i<row[k]*col[k]; i++){
					person[k][i] =0;
					density[k][i] =1;
				}
			}
			int Time = 0; // to calculate the time of evacuation
			int w;

			Time = 2;
			while(check()==1){

				for (i=0; i<numbering; i++){
					stepmark[i] = 0;
				}
				for (k=floors-1; k>=0; k--){
					constructg(k);
					for (j=0; j<row[k]*col[k]; j++){
						flag[k][j] = 0;
						countofpos[k][j] = 0;
					}
					for (i=0; i<noOfDoor[k]; i++){
						bfs(door[k][i],k);
					}
					evacuate(k,Time,numbering);
				}
				Time++;
			}

			for(i=2;i<numbering;i++){

				for(j=0;j<2;j++){

					next[i][j] = next[i][0];
					floorofn[i][j] = floorofn[i][0];
				}
			}
			Frame frame1 = new MainFrame(new BuildingApp(grid,row,col,floors
						,next,Time,numbering,floorofn,noOfDoor,door,1.000f - speed), 868, 868);
			

			Time -= 2;
			System.out.println("Total Time For Evacuation " + Time );
			String out;
			for(i=0;i<noOfDoor[0];i++){
				out = " ";
				out = "Person Evacuated From Door"+ " " + door[0][i]/col[0] + " " + door[0][i]%col[0] + " are " + personfromdoor[0][i];
				System.out.println("No Of Persons Evacatued From The Door " + door[0][i]/col[0] + " " 
						+ door[0][i]%col[0] + " are " + personfromdoor[0][i]); 
				

			}
			out = "TIme Of Evacuation " + Time;


		}
		catch(NullPointerException e){}

	}

	public static void constructg(int floor)
	{
		int i ; //loop counter
		int j ; // loop counter
		int[] x1 = new int[10]; // x co-ordinate of a 1 square of grid ( one of the eight positions ) 
		int[] y1 = new int[10]; // y co-ordinate of a 1 square of grid ( one of the eight positions ) 
		int[] val = new int[10];// saving the value of the  sqaure (x,y)

		int x ; // temp variable to store the x co-ordinate 
		int y ; // temp variable to store y co-ordinate

		for (i=0; i<row[floor]*col[floor]; i++){
			x = i/col[floor];
			y=  i%col[floor];

			if(grid[floor][x][y] > 1 || grid[floor][x][y] == 0)
			{
				if(y + 1 < col[floor]){
					x1[0] = (x);
					y1[0] = y+1;
					val[0] = i + 1;
				}else{ 
					x1[0] = row[floor]+1;
				}

				if(y-1 >= 0){
					x1[1] = (x);
					y1[1] = (y-1);
					val[1] = i - 1;
				}else{
					x1[1] = row[floor] + 1;
				}

				if(x + 1 < row[floor]){
					x1[2]=(x+1);
					y1[2]=y;
					val[2] = i + col[floor];
				}else{
					x1[2] = row[floor] + 1;
				}

				if(x-1>=0){
					x1[3]= (x-1);
					y1[3]= y;
					val[3] = i - col[floor];
				}else{
					x1[3] =  row[floor] + 1;
				}

				if(x + 1 < row[floor] && y + 1 < col[floor]){
					x1[4] = (x+1);
					y1[4]=((y+1));
					val[4] = i + col[floor] + 1;
				}else{
					x1[4] =  row[floor] + 1;
				}

				if(x-1>=0 && y +1 < col[floor]){
					x1[5] =(x-1);
					y1[5]=((y+1));
					val[5]= i - col[floor] + 1;
				}else{
					x1[5] =  row[floor] + 1;
				}
				if(x+1 < row[floor] && y - 1 >= 0){
					x1[6] =(x+1);
					y1[6]=((y-1));
					val[6]= i  + col[floor] - 1;
				}else{
					x1[6] =  row[floor] + 1;
				}
				if (x-1 >=0 && y - 1>=0){
					x1[7] =(x-1);
					y1[7]=((y-1));
					val[7]=i-col[floor]-1;
				}else{
					x1[7] =  row[floor] + 1;
				}
				for (j=0; j<8; j++){
					if((val[j] >= 0 && val[j] < row[floor]*col[floor]) 
							&& ( x1[j] >= 0 && x1[j] < row[floor])&&(y1[j]>=0&&y1[j]<col[floor])){
						if(grid[floor][x1[j]][y1[j]] != 1)
						{	
							graph[floor][i][val[j]] = 1;
							graph[floor][val[j]][i]=1;
						}
					}
				}
			}
		}

	}

	public static int check()
	{
		/*loop counters */
		int i;
		int j;
		int k;

		for (k=0; k<floors; k++){
			for (i=0; i<row[k]; i++){
				for (j=0; j<col[k]; j++){

					if(grid[k][i][j] > 1)
						return 1;
				}
			}
		}
		return 0;
	}

	public static void evacuate( int floor , int time, int numbering)
	{
		int[] move = new int[10000];//array to ensure that every person has moved or not
		int i ; //loop counter
		int j ; // loop counter
		int temp = 0; // temp variable to store temporary value of grid
		int count = 0;//count the no of moves


		for(i=0;i<row[floor]*col[floor];i++){
			move[i] = 0;
		}

		while(mcheck(move,row[floor]*col[floor],floor) == 1){

			count++;
			if(count > (row[floor]+col[floor]) )
				break;
			for (i=0; i<row[floor]*col[floor]; i++){

				if(flag[floor][i] == 1&&move[i] == 0){

					for (j=0; j<noOfDoor[floor]; j++){
						if(door[floor][j] == i)
						{
							temp = grid[floor][i/col[floor]][i%col[floor]];
							if(floor == 0){
								grid[floor][i/col[floor]][i%col[floor]] = 0;
							}
							if(floor ==  1){
								int  endpoint;//temp variable to store the value of stairs end point
								endpoint = stairend[1][j][0];
								if(grid[0][endpoint/col[0]][endpoint%col[floor]] == 0){

									grid[floor][i/col[floor]][i%col[floor]] = 0;
									grid[0][endpoint/col[0]][endpoint%col[0]] = temp;
									next[temp][time] = endpoint;
									floorofn[temp][time] = 0;
									stepmark[temp] = 1;
								}
							}
							move[i] = 1;
							temp = 1;
							personfromdoor[floor][j]++;
						}
					}
					int z = position[floor][i][0]; //temp variable to store the value
					if( grid[floor][z/col[floor]][z%col[floor]] == 0 && move[i] == 0)
					{
						grid[floor][z/col[floor]][z%col[floor]] = grid[floor][i/col[floor]][i%col[floor]];
						grid[floor][i/col[floor]][i%col[floor]] = 0;
						move[i] = 1;

					}
				}
			}
		}


		int w; //loop counter
		int c; //loop counter

		for (i=0; i<row[floor]; i++){
			for (j=0; j<col[floor]; j++){
				for (w=2; w<numbering; w++){
					if((grid[floor][i][j]==w) && (floor==0) && (stepmark[w]==0)){

						next[w][time] = i*col[floor] + j;
						stepmark[w] = 1;
						if(floorofn[w][time] == 0){
							for (c = 0; c < noOfDoor[0]; c++){

								if(door[0][c] == next[w][time]){
									int u;
									for(u = time; u < 100 ;u++){
										next[w][u] = door[0][c];
									}
									floorofn[w][time] = -1;
								}
							}
						}
						floorofn[w][time] = 0;

					}else if((grid[floor][i][j] ==w) && (floor > 0)){
						next[w][time] = i*col[floor] + j;
						stepmark[w] =  1;
						floorofn[w][time] = 1;
					}	
				}
			}
		}

	}

	public static int mcheck(int[] move,int num,int floor)
	{
		int i;
		for (i=0; i<num; i++){

			if(flag[floor][i] == 1){
				if(move[i] == 0){
					return 1;
				}
			}
		}
		return 0;
	}



	public static void pprint(int[] path,int doorend,int destination ,int floor)
	{
		int i = doorend;//store door end
		int s = 0;//count the steps to reach the door
		int j;//loop counter
		int[] save = new int[10001];//save the path 
		while( path[i] != destination){
			if(path[i] == -1){
				break;
			}
			save[s] = path[i];
			s++;
			i = path[i];
		}
		flag[floor][doorend] = 1;
		if( (countofpos[floor][doorend] == 0) || (density[floor][doorend] > s*person[floor][destination]) ){
			for (i=0; i<s; i++){
				position[floor][doorend][i] = save[i];
			}
			position[floor][doorend][s] = destination;
			countofpos[floor][doorend] = s + 1;
			person[floor][destination]++;
			density[floor][doorend] = (s)*person[floor][destination];
			s++;
		}
	}

	public static void bfs(int destination,int floor)
	{
		int i; //loop counter
		int j; // loop counter
		int[] mark = new int[100000];//mark the vertex 
		int[] path = new int[100000];//saving the path from source to destination

		for( i=0; i<row[floor]*col[floor]; i++){
			mark[i] = 0;
			path[i] = -1;
		}
		int[] que = new int[100000];//que to enquee and dequee the vertices
		int top = 0; // pointing current top index of que or to traverse through que
		int front = 0;//pointing front index of que
		int frontvalue; // temp variable to store the frontvalue of que
		que[top] = destination;
		top++ ;
		mark[destination] = 1;
		path[destination] = 0;

		while(top > front){

			frontvalue = que[front]; // temp variable to store the frontvalue of que
			for (i=0; i<row[floor]*col[floor]; i++){
				if(graph[floor][frontvalue][i]!=0){
					if(mark[i] != 1){
						que[top] = i;	
						top++;
						mark[i] = 1;
						path[i] = frontvalue;
					}
				}
			}
			mark[frontvalue] = 1;
			front++;
		}
		for (i=0; i<row[floor]*col[floor]; i++){
			if((path[i] != -1) && (grid[floor][i/col[floor]][i%col[floor]]>1) ){
				pprint(path,i,destination,floor);
			}
		}
	}	
}

