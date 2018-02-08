/**
 * 
 */
package tools.vitruv.extensions.changevisualization.tree;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.util.HashSet;

import javax.swing.JTree;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeNode;

/**
 * Used by the JTree to visualize individual nodes. The implementation is regarded as a quick hack so far and will be replaced
 * soon. Do not consider during code review.
 * 
 * @author Andreas Löffler
 *
 */
public class ChangeNodeRenderer extends DefaultTreeCellRenderer implements TreeCellRenderer {

	private static final Color NAME_HIGHLIGHT_FOREGROUND_COLOR=Color.BLACK;
	private static final Color NAME_HIGHLIGHT_BACKGROUND_COLOR=new Color(200,200,255);
	private static final Color SEARCH_HIGHLIGHT_COLOR=new Color(200,255,200);
	
	private boolean searchHighlight=false;
	private HashSet<TreeNode> highlightedNodes=new HashSet<TreeNode>();

	@Override
	public void paint(Graphics g) {		
			
		int firstIndex=getNameFirstIndex();
		//System.out.println(this.getText()+" ===> "+firstIndex);				
		if(firstIndex!=-1) {
			
			String origText=this.getText();			

			//change text temporarily and rely on getPreferedSize() to determine where the name is drawn
			setText(origText.substring(0,firstIndex-1));
			Dimension size = getPreferredSize();
			setText(origText);

			BufferedImage img=paintToImage();
			
			
			if(isSearchHighlight()) {
				Color background=selected?this.getBackgroundSelectionColor():this.getBackground();
				replaceColor(img,background,SEARCH_HIGHLIGHT_COLOR,0,0,size.width,img.getHeight());			
			}			

			Color foreground=this.getForeground();
			Color background=selected?this.getBackgroundSelectionColor():this.getBackground();			
			
			if(NAME_HIGHLIGHT_FOREGROUND_COLOR!=null&&NAME_HIGHLIGHT_FOREGROUND_COLOR.getRGB()!=foreground.getRGB()) {
				replaceColor(img,foreground,NAME_HIGHLIGHT_FOREGROUND_COLOR,size.width+1,0,img.getWidth(),img.getHeight());
				//foreground=NAME_HIGHLIGHT_FOREGROUND_COLOR;//necessary for background-replacement to work
			}
			if(NAME_HIGHLIGHT_BACKGROUND_COLOR!=null&&NAME_HIGHLIGHT_BACKGROUND_COLOR.getRGB()!=background.getRGB()) {				
				replaceColor(img,background,NAME_HIGHLIGHT_BACKGROUND_COLOR,size.width+1,0,img.getWidth(),img.getHeight());
				//setBackgroundToColor(img,foreground,NAME_HIGHLIGHT_BACKGROUND_COLOR,size.width+1,0,img.getWidth(),img.getHeight());
			}

			g.drawImage(img, 0, 0,null);

		}else {
			if(isSearchHighlight()) {
				BufferedImage img=paintToImage();
				Color background=selected?this.getBackgroundSelectionColor():this.getBackground();
				replaceColor(img,background,SEARCH_HIGHLIGHT_COLOR,0,0,img.getWidth(),img.getHeight());
				//setBackgroundToColor(img,Color.BLACK,SEARCH_HIGHLIGHT_COLOR,0,0,img.getWidth(),img.getHeight());
				g.drawImage(img, 0, 0,null);
			}else {
				super.paint(g);
			}
		}

	}

	private void replaceColor(BufferedImage img, Color oldColor, Color newColor, int firstX, int firstY, int width,
			int height) {
		for(int x=firstX;x<width;x++) {
			for(int y=firstY;y<height;y++) {					
				if(img.getRGB(x, y)==oldColor.getRGB()) {
					img.setRGB(x, y, newColor.getRGB());
				}
			}	
		}
	}
	
	/**
	 * replace the color of all pixels that is not the given stayColor with the replacementColor
	 * 
	 * @param img
	 * @param oldColor
	 * @param newColor
	 * @param firstX
	 * @param firstY
	 * @param width
	 * @param height
	 */
	private void setBackgroundToColor(BufferedImage img, Color stayColor, Color newColor, int firstX, int firstY, int width,
			int height) {
		for(int x=firstX;x<width;x++) {
			for(int y=firstY;y<height;y++) {					
				if(img.getRGB(x, y)!=stayColor.getRGB()) {
					img.setRGB(x, y, newColor.getRGB());
				}
			}	
		}
	}

	private BufferedImage paintToImage() {
		BufferedImage img=new BufferedImage(getWidth(),getHeight(),BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d=img.createGraphics();
		super.paint(g2d);
		return img;
	}

	private int getNameFirstIndex() {
		String text=this.getText();
		if(text==null) return -1;
		int firstIndexOfName=text.lastIndexOf("[");
		if(firstIndexOfName==-1) {
			return -1;
		}
		int lastIndexOfName=text.lastIndexOf("]");
		if(lastIndexOfName==-1) {
			return -1;
		}
		if(lastIndexOfName<=firstIndexOfName) {
			return -1;
		}
		return firstIndexOfName;
	}

	@Override
	public Component getTreeCellRendererComponent(JTree tree, Object value,
			boolean sel,
			boolean expanded,
			boolean leaf, int row,
			boolean hasFocus) {
		////Reset background
		//this.setBackground(tree.getBackground());
		////System.out.print("opaque?"+this.isOpaque());
		//this.setOpaque(false);

		//Get default visualization
		//Thread.dumpStack();
		Component comp=super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);

		//apply highlighting if necessary
		if(shouldSearchHighlight(value)) {
			//System.out.println("Highlighting : "+value);
			searchHighlight=true;
		}else {
			searchHighlight=false;
		}
		return comp;
	}
	
	public void resetHighligthedNodes() {
		highlightedNodes.clear();
	}

	public void highlightNode(TreeNode node) {		
		//Highlithing does not work yet as expected ==> deactivated
		//highlightedNodes.add(node);
	}
	
	private boolean isSearchHighlight() {
		return this.searchHighlight;
	}

	private boolean shouldSearchHighlight(Object value) {
		return highlightedNodes.contains(value);
	}
	
}
