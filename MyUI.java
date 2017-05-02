package my.vaadin.app;

import java.util.ArrayList;
import java.util.Set;

import javax.servlet.annotation.WebServlet;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.VaadinServletConfiguration;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinServlet;
import com.vaadin.server.ClientConnector.AttachEvent;
import com.vaadin.server.ClientConnector.AttachListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Grid;
import com.vaadin.ui.Label;
import com.vaadin.ui.Notification;
import com.vaadin.ui.SingleSelect;
import com.vaadin.ui.TextField;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Button.ClickEvent;





/**
 * This UI is the application entry point. A UI may either represent a browser window 
 * (or tab) or some part of a html page where a Vaadin application is embedded.
 * <p>
 * The UI is initialized using {@link #init(VaadinRequest)}. This method is intended to be 
 * overridden to add component to the user interface and initialize non-component functionality.
 */
@Theme("mytheme")
public class MyUI extends UI {

	 private Button addButton = new Button("Add");
     private Button deleteButton = new Button ("Delete");
     private String textFilter;
     private Set<Info> selected; 
     private SingleSelect<Info> selection;
    
    @Override
    protected void init(VaadinRequest vaadinRequest) {
    	
    	 
    	 ArrayList<Info> Information = new ArrayList<Info>();
         VerticalLayout form = new VerticalLayout();
         
          setContent(form);
          
          TextField tf1 = new TextField("infor1");
          tf1.setRequiredIndicatorVisible(true);
          form.addComponent(tf1);

          TextField tf2 = new TextField("infor2");
          form.addComponent(tf2);

          TextField tf3 = new TextField("infor3");
          form.addComponent(tf3);
          
          
          Grid<Info> grid = new Grid<>();
          

          TextField taskField1 = new TextField();
          TextField taskField2 = new TextField();
          TextField taskField3 = new TextField();
          
          grid.setCaption("Grid");
          grid.setItems(Information);
          grid.addColumn(Info::getInfo2).setCaption("Information").setEditorComponent(taskField1, Info::setInfo2).setExpandRatio(1);;
          grid.addColumn(Info::getInfo1).setCaption("Information").setEditorComponent(taskField2, Info::setInfo1).setExpandRatio(1);
          grid.addColumn(Info:: getInfo3).setCaption("Information").setEditorComponent(taskField3, Info::setInfo3).setExpandRatio(1);
          selection = grid.asSingleSelect();
          
         
         
          grid.getEditor().setEnabled(true);
          grid.setVisible(true);
          
          
          addButton.addClickListener(new Button.ClickListener() {
  			
  			@Override
  			public void buttonClick(ClickEvent event) {
  				Info add = new Info();
  				add.setInfo2(tf1.getValue().toString());
  				add.setInfo1(tf2.getValue().toString());
  				add.setInfo3(tf3.getValue().toString());
  				Information.add(add);
  				grid.clearSortOrder();
  				Notification.show("Added");
  			}
  		});
          
          form.addComponent(addButton);
          
       
          form.addComponent(grid);
          
      }
    
	@WebServlet(urlPatterns = "/*", name = "MyUIServlet", asyncSupported = true)
   @VaadinServletConfiguration(ui = MyUI.class, productionMode = false)
   public static class MyUIServlet extends VaadinServlet {
   }

}
