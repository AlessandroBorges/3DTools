package tools3d.utils;

import java.awt.GraphicsConfigTemplate;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java3d.nativelinker.Java3dLinker2;

import javax.media.j3d.Canvas3D;
import javax.media.j3d.GraphicsConfigTemplate3D;
import javax.media.j3d.VirtualUniverse;
import javax.swing.JTextArea;
@SuppressWarnings("all")
public class QueryProperties extends javax.swing.JFrame
{

	public static void printProps(JTextArea textArea, Map map, String[] propList)
	{
		// Create an alphabetical list of keys
		List<String> keyList = new ArrayList<String>(map.keySet());
		Collections.sort(keyList);
		Iterator<String> it;

		// Collection used to remember the properties we've already
		// printed, so we don't print them twice
		HashSet<String> hs = new HashSet<String>();

		// Print out the values for the caller-specified properties
		String key;
		for (int i = 0; i < propList.length; i++)
		{
			int len = propList[i].length();
			int idxWild = propList[i].indexOf('*');
			if (idxWild < 0)
			{
				key = propList[i];
				if (!hs.contains(key))
				{
					textArea.append(key + " = " + map.get(key) + "\n");
					hs.add(key);
				}
			}
			else if (idxWild == len - 1)
			{
				String pattern = propList[i].substring(0, len - 1);
				it = keyList.iterator();
				while (it.hasNext())
				{
					key = it.next();
					if (key.startsWith(pattern) && !hs.contains(key))
					{
						textArea.append(key + " = " + map.get(key) + "\n");
						hs.add(key);
					}
				}
			}
			else
			{
				textArea.append(propList[i] + " = ERROR: KEY WITH EMBEDDED WILD CARD IGNORED\n");
			}
		}

		// Print out the values for those properties not already printed
		it = keyList.iterator();
		while (it.hasNext())
		{
			key = it.next();
			if (!hs.contains(key))
			{
				textArea.append(key + " = " + map.get(key) + "\n");
			}
		}
		
		//also bang everythign else out on teh console for fun
		Properties props = System.getProperties();
		props.list(System.out);

	}

	
	public QueryProperties()
	{
		initComponents();

		//VirtualUniverse vu = new VirtualUniverse();
		Map vuMap = VirtualUniverse.getProperties();
		final String[] vuPropList =
		{ "j3d.version", "j3d.vendor", "j3d.specification.version", "j3d.specification.vendor", "j3d.*"
		// Just print all other properties in alphabetical order
		};

		printProps(myTextArea, vuMap, vuPropList);
		myTextArea.append("\n");

		GraphicsConfigTemplate3D template = new GraphicsConfigTemplate3D();

		/* We need to set this to force choosing a pixel format
		   that support the canvas.
		*/
		template.setStereo(GraphicsConfigTemplate.PREFERRED);
		template.setSceneAntialiasing(GraphicsConfigTemplate.PREFERRED);

		GraphicsConfiguration config = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice()
				.getBestConfiguration(template);

		Map c3dMap = new Canvas3D(config).queryProperties();
		final String[] c3dPropList =
		{ "native.*", "doubleBufferAvailable", "stereoAvailable", "sceneAntialiasing*", "compressedGeometry.majorVersionNumber",
				"compressedGeometry.minorVersionNumber", "compressedGeometry.*", "textureUnitStateMax", "textureWidthMax",
				"textureHeightMax",
		// Just print all other properties in alphabetical order
		};
		
		
		

		printProps(myTextArea, c3dMap, c3dPropList);
	}

	// ----------------------------------------------------------------

	/** This method is called from within the constructor to
	 * initialize the form.
	 * WARNING: Do NOT modify this code. The content of this method is
	 * always regenerated by the Form Editor.
	 */
	// <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
	private void initComponents()
	{
		jScrollPane1 = new javax.swing.JScrollPane();
		myTextArea = new javax.swing.JTextArea();

		setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
		setTitle("QueryProperties");
		jScrollPane1.setPreferredSize(new java.awt.Dimension(400, 500));
		myTextArea.setColumns(20);
		myTextArea.setEditable(false);
		myTextArea.setRows(5);
		jScrollPane1.setViewportView(myTextArea);

		getContentPane().add(jScrollPane1, java.awt.BorderLayout.CENTER);

		pack();
	}// </editor-fold>//GEN-END:initComponents

	/**
	 * @param args the command line arguments
	 */
	public static void main(String args[])
	{
		new Java3dLinker2();
		java.awt.EventQueue.invokeLater(new Runnable()
		{
			public void run()
			{
				new QueryProperties().setVisible(true);
			}
		});
	}

	// Variables declaration - do not modify//GEN-BEGIN:variables
	private javax.swing.JScrollPane jScrollPane1;

	private javax.swing.JTextArea myTextArea;
	// End of variables declaration//GEN-END:variables

}