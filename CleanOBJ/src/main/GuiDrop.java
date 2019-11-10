package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDragEvent;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetEvent;
import java.awt.dnd.DropTargetListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JTextArea;

public class GuiDrop extends JFrame implements DropTargetListener
{
	private static final long serialVersionUID = 1L;
	
	DropTarget dt;
    JTextArea ta;
    
    public GuiDrop()
    {
        super("Drag&Drop OBJ Cleaner");
        setSize(512, 256);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ta = new JTextArea();
        ta.setBackground(Color.WHITE);
        getContentPane().add(ta, BorderLayout.CENTER);

        dt = new DropTarget(ta, this);
        setVisible(true);
    }
     
    public void dragEnter(DropTargetDragEvent dtde) {}
 
    public void dragExit(DropTargetEvent dte) {}
 
    public void dragOver(DropTargetDragEvent dtde) {}
 
    public void dropActionChanged(DropTargetDragEvent dtde) {}
 
    public void drop(DropTargetDropEvent dtde)
    {
        try
        {
        	Transferable tr = dtde.getTransferable();
        	DataFlavor[] flavors = tr.getTransferDataFlavors();
        	ta.setText("");
        	for (int i = 0; i < flavors.length; i++)
        	{
        		if (flavors[i].isFlavorJavaFileListType())
        		{
        			dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        			@SuppressWarnings("unchecked")
					List<Object> list = (List<Object>)tr.getTransferData(flavors[i]);
        			for (int j = 0; j < list.size(); j++)
        			{
        				ta.append(list.get(j)+"... ");
        				System.out.println(list.get(j).toString());
        				Main.startCleanObj(list.get(j).toString());
        				ta.append("done." + "\n");
        			}

        			dtde.dropComplete(true);
        			return;
        		}
        		else if (flavors[i].isFlavorSerializedObjectType())
        		{
        			dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        			dtde.dropComplete(true);
        			return;
        		}
        		else if (flavors[i].isRepresentationClassInputStream())
        		{
        			dtde.acceptDrop(DnDConstants.ACTION_COPY_OR_MOVE);
        			dtde.dropComplete(true);
        			return;
        		}
        	}

        	dtde.rejectDrop();
        }
        catch (Exception e)
        {
            e.printStackTrace();
            dtde.rejectDrop();
        }
    }
}
