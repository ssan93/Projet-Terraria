package controller;

import java.net.URL;

import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.game.BillView;
import view.game.MapView;
import model.game.Map;

public class GameController extends Controller{

	private static final int gauche = -32, droite = 1952;

	// liste observable ou liste simple ??
	private static ArrayList<KeyCode> keyPressed = new ArrayList<>();

	@FXML
	private AnchorPane background;

	@FXML
	private Pane floor;

	@FXML
	private Pane charapane;

	private MapView mv;

	Map tileSol = new Map("src/maps/carte.txt", "src/maps/carte2.txt", "src/maps/carte3.txt");
	int temps;
	private Timeline loop;
	BillView bill = new BillView("view/resources/personnages/right_static_bill.png");
	String oldAnim = "tactac";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		mv = new MapView(tileSol);
		// initAnimation();
		// loop.play();
		bill.getChrac().setSpeed(4);
		bill.getChrac().getXProperty().set(32 * 5);
		bill.getChrac().getYProperty().set(32 * 11);
		charapane.getChildren().add(bill.getImage());
		floor.getChildren().addAll(mv.creerVue());
	}

	public void actions() {
		if (keyPressed.contains(KeyCode.D) || keyPressed.contains(KeyCode.RIGHT)) {
			if (!stopSroll().equals("right stop")) {

				bill.getChrac().animation("RunRight");
				oldAnim = "RunRight";
				removeImages("Right");

			}
		}
		if (keyPressed.contains(KeyCode.Q) || keyPressed.contains(KeyCode.LEFT)) {
			if (!stopSroll().equals("left stop")) {
				bill.getChrac().animation("RunLeft");
				oldAnim = "RunLeft";
				removeImages("Left");
			}
		}
	}

	public void removeImages(String direction) {
		switch (direction) {
		case "Right":

			for (int i = 0; i < floor.getChildren().size(); i++) {

				floor.getChildren().get(i).relocate(
						floor.getChildren().get(i).getLayoutX() - bill.getChrac().getSpeed(),
						floor.getChildren().get(i).getLayoutY());
				if (floor.getChildren().get(i).getLayoutX() < -32) {
					floor.getChildren().remove(floor.getChildren().get(i));
				}

			}
			break;

		case "Left":
			for (int i = floor.getChildren().size() - 1; i >= 0; i--) {

				floor.getChildren().get(i).relocate(
						floor.getChildren().get(i).getLayoutX() + bill.getChrac().getSpeed(),
						floor.getChildren().get(i).getLayoutY());
				if (floor.getChildren().get(i).getLayoutX() > 32 * 30) {
					floor.getChildren().remove(floor.getChildren().get(i));
				}
			}
			break;
		}

	}

	public String stopSroll() {

		if (floor.getChildren().get(1).getLayoutX() == charapane.getLayoutX() + bill.getChrac().getX() + 64) {
			return "left stop";
		}
		System.out.println(floor.getChildren().size());

		if (floor.getChildren().get(floor.getChildren().size() - 1).getLayoutX() == charapane.getLayoutX()
				+ bill.getChrac().getX() + 32) {
			return "right stop";
		}
		return "";
	}

	public void stopAction() {
		switch (oldAnim) {
		case "RunRight":
			bill.getChrac().animation("idleRight");
			oldAnim = "idleRight";
			break;
		case "RunLeft":
			bill.getChrac().animation("idleLeft");
			oldAnim = "idleLeft";
			break;
		}
	}

	public void initAnimation() {
		loop = new Timeline();
		loop.setCycleCount(Timeline.INDEFINITE);
		System.out.println(temps);

		KeyFrame kf = new KeyFrame(Duration.seconds(0.033), (ev -> {

			temps++;
		}));
		loop.getKeyFrames().add(kf);
	}

	public void addKeyCode(KeyCode k) {
		if (!keyPressed.contains(k))
			keyPressed.add(k);
	}

	public void removeKeyCode(KeyCode k) {
		keyPressed.remove(k);
	}

}
