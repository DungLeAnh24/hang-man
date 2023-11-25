package application;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

																					
public class Main extends Application {

	private static double xOffset = 0;
	private static double yOffset = 0;
	
	@Override
	public void start(Stage primaryStage) {
		try
		{
			primaryStage.setAlwaysOnTop(true);
			primaryStage.getIcons().add(new Image("/imageAssets/hangmanIcon.jpg"));


			FXMLLoader root = new FXMLLoader(getClass().getResource("HangmanFile.fxml"));
		    Parent gameScenePane = root.load();
		    Scene gameScene = new Scene(gameScenePane);
		    
		    
		    FXMLLoader minPaneLoader = new FXMLLoader(getClass().getResource("HangmanMinimized.fxml"));
	        Parent minSceneLoader = minPaneLoader.load();
	        Scene minScene = new Scene(minSceneLoader);
	        
	        FXMLLoader optionsPaneLoader = new FXMLLoader(getClass().getResource("HangmanOptions.fxml"));
	        Parent optionsSceneLoader = optionsPaneLoader.load();
	        Scene optionsScene = new Scene(optionsSceneLoader);


	        HangmanController rootPaneController = (HangmanController) root.getController();
	        rootPaneController.setMinScene(minScene);
	        rootPaneController.setOptionsScene(optionsScene);
	        rootPaneController.setMainParent(gameScenePane);


	        MinimizedController minPaneController = (MinimizedController) minPaneLoader.getController();//
	        minPaneController.setMainScene(gameScene);
	        minPaneController.setOptionsScene(optionsScene);
        

	        OptionsController optionsPaneController = (OptionsController) optionsPaneLoader.getController();//
	        optionsPaneController.setMainScene(gameScene);
	        optionsPaneController.setMinScene(minScene);
	        

	        optionsPaneController.setRootPaneController(rootPaneController);
	        optionsPaneController.setMinPaneController(minPaneController);
	        minPaneController.setRootPaneController(rootPaneController);
	        minPaneController.setOptionsPaneController(optionsPaneController);
	        rootPaneController.setMinPaneController(minPaneController);
	        rootPaneController.setOptionsPaneController(optionsPaneController);


			primaryStage.initStyle(StageStyle.TRANSPARENT);
			

			gameScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			optionsScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			minScene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());

			gameScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
			optionsScene.setFill(javafx.scene.paint.Color.TRANSPARENT);
			minScene.setFill(javafx.scene.paint.Color.TRANSPARENT);

			gameScenePane.requestFocus();

			primaryStage.setScene(gameScene);
			primaryStage.show();


			makeParentDraggable(gameScenePane, primaryStage);
			makeParentDraggable(minSceneLoader, primaryStage);
			makeParentDraggable(optionsSceneLoader, primaryStage);        
	        
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void main(String[] args)
	{
		launch(args);
	}

	public static void makeParentDraggable(Parent loaderToDrag, Stage primaryStage)//and transparent
	{
		loaderToDrag.setOnMousePressed(new EventHandler<MouseEvent>()
		{
            @Override
            public void handle(MouseEvent event)
            {
                xOffset = event.getSceneX();
                yOffset = event.getSceneY();
            }
        });
        loaderToDrag.setOnMouseDragged(new EventHandler<MouseEvent>()
        {
            @Override
            public void handle(MouseEvent event)
            {
                primaryStage.setX(event.getScreenX() - xOffset);
                primaryStage.setY(event.getScreenY() - yOffset);
                primaryStage.setOpacity(0.7f);
            }
        });
        loaderToDrag.setOnDragDone(e ->
        {
        	primaryStage.setOpacity(1.0f);
        });
        loaderToDrag.setOnMouseReleased(e ->
        {
        	primaryStage.setOpacity(1.0f);
        });
	}
}
