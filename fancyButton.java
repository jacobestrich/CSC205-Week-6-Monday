import javax.swing.BorderFactory;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class fancyButton extends JButton
{
    public fancyButton()
    {
        super();  
        
        MouseAdapter mouseAdapter = new MouseAdapter() 
        {   
            @Override
            public void mouseEntered(MouseEvent e) 
            {
                setBorder(BorderFactory.createLineBorder(Color.yellow));
            }
            @Override
            public void mouseExited(MouseEvent e) 
            {
                setBorder(BorderFactory.createLineBorder(Color.gray));
            }
        };        

        addMouseListener(mouseAdapter);
    }


}
