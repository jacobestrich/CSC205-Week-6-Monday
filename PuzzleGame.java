import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;


public class PuzzleGame extends JFrame
{
    private final int WIDTH = 700;
    private int HEIGHT = 700;
    private final int COLUMNS = 3, ROWS = 4;
    private ArrayList<fancyButton> buttonList;
    private ArrayList<fancyButton> buttonSolution = new ArrayList<fancyButton>();
    private BufferedImage imageSource;
    private BufferedImage imageResized;
    JPanel panel;
    


    public PuzzleGame() 
    {
        super("Puzzle Game");

        panel = new JPanel(); // sets a new instance of the JPanel
        panel.setLayout(new GridLayout(ROWS, COLUMNS));
        add(panel);

        try
        {
           imageSource = loadImage();
           int sourceWidth = imageSource.getWidth();
           int sourceHeight = imageSource.getHeight();
           double aspectRatio = (double)sourceHeight / sourceWidth;
           HEIGHT = (int)(aspectRatio * WIDTH);

           imageResized = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
           Graphics2D graphics = imageResized.createGraphics();
           graphics.drawImage(imageSource, 0, 0, WIDTH, HEIGHT, null);
           graphics.dispose();
        }
        catch (IOException e)
        {
            JOptionPane.showMessageDialog(rootPane, "Image is not found!");
        }
       

        buttonList = new ArrayList<fancyButton>();

        for(int i = 0; i < COLUMNS * ROWS; i++)
        {
            int row = i / COLUMNS;
            int column = i % COLUMNS;

            Image imageslice = createImage(
                new FilteredImageSource(imageResized.getSource(), 
                new CropImageFilter(column * WIDTH/COLUMNS, row * HEIGHT/ROWS, WIDTH/COLUMNS, HEIGHT/ROWS)));


            fancyButton btn = new fancyButton();

            btn.addActionListener(e -> myClickEventHandler(e));

            if(i == COLUMNS * ROWS -1)
            {
                btn.setBorderPainted(false);
                btn.setContentAreaFilled(false);
            }
            else 
            {
                btn.setIcon(new ImageIcon(imageslice));
            }


            buttonSolution.add(btn);
            buttonList.add(btn);       
        }

        
        Collections.shuffle(buttonList);
        for(var button : buttonList)
        {
            panel.add(button);
        }

        setLocation(1500, 150);
        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }

    private void myClickEventHandler(ActionEvent e)
    {
        fancyButton btnClicked = (fancyButton)e.getSource();
        int i = buttonList.indexOf(btnClicked);
        int row = i / COLUMNS;
        int column = i % COLUMNS;

        int emptyIndex = -1;
        for(int j = 0; j < buttonList.size(); j++)
        {
            if(buttonList.get(j).getIcon() == null)
            {
                emptyIndex = j;
            }
        }

        int emptyRow = emptyIndex / COLUMNS;
        int emptyColumn = emptyIndex % COLUMNS;

        if(emptyRow == row && Math.abs(column - emptyColumn) == 1 ||
        emptyColumn == column && Math.abs(row - emptyRow) == 1)
        {
            Collections.swap(buttonList, i, emptyIndex);
            updateButtons();
        }

        if(buttonSolution.equals(buttonList))
        {
            JOptionPane.showMessageDialog(rootPane, "You Won!");
        }

    }

    public void updateButtons()
    {
        panel.removeAll();
        for(var btn : buttonList)
        {
            panel.add(btn);
        }
        panel.validate();
    }




    private BufferedImage loadImage() throws IOException
    {       
        return ImageIO.read(new File("dk.jpg"));
    }

}
