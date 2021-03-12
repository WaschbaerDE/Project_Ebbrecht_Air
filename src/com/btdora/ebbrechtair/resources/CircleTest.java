import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class CircleTest extends Application
{
    public static void main(String[] args)
    {
        Application.launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        // Create the Canvas with a width of 400 px and a height of 200 px.
        Canvas canvas = new Canvas(400, 200);
        // Get the graphics context of the canvas
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.fillOval(70, 70, 70, 70);
        // Draw a filled Oval




        // Create the Pane
        Pane root = new Pane();


        // Add the Canvas to the Pane
        root.getChildren().add(canvas);
        // Create the Scene
        Scene scene = new Scene(root);
        // Add the Scene to the Stage
        stage.setScene(scene);
        // Set the Title of the Stage
        stage.setTitle("Drawing Basic Shapes on a Canvas");
        // Display the Stage
        stage.show();
    }
}