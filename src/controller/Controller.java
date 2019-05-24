package controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import view.game.BillView;
import view.game.MapView;
import model.game.Map;

public class Controller implements Initializable {

	private static final int gauche = -32, droite = 1952;

//	private SimpleIntegerProperty absolute_x, absolute_y, absolute_charactX, absolute_charactY;

	// liste observable ou liste simple ??
	private static ArrayList<KeyCode> keyPressed = new ArrayList<>();

	@FXML
	private AnchorPane background;

	@FXML
	private Pane floor;

	@FXML
	private Pane charapane;

	private Map mapPrincipal = new Map("src/maps/grosseMap_sol.csv", "src/maps/grosseMap_environnement.csv"/*, "src/maps/carte3.txt"*/);
	private MapView mv;
	private Timeline loop;
	private int temps;
	private BillView bill = new BillView("view/resources/personnages/right_static_bill.png");
	private String oldAnim = "tactac";

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		background.getChildren().add(0, new ImageView(new Image("view/resources/tac.jpg")));
//		absolute_x = new SimpleIntegerProperty(0);
//		absolute_y = new SimpleIntegerProperty(0);
//		absolute_charactX = new SimpleIntegerProperty();
//		absolute_charactY = new SimpleIntegerProperty();
//		absolute_charactX.bind(bill.getChrac().getXProperty());
//		absolute_charactY.bind(bill.getChrac().getYProperty());
		mv = new MapView(mapPrincipal);
		initAnimation();
		loop.play();
		bill.getChrac().setSpeed(4);
		charapane.getChildren().add(bill.getImage());
		floor.getChildren().addAll(mv.creerVue());
	}

	public void actions() {
		if (keyPressed.contains(KeyCode.D) || keyPressed.contains(KeyCode.RIGHT)) {
			bill.getChrac().animation("RunRight");
			oldAnim = "RunRight";
			removeImages("Right");
		}

		if (keyPressed.contains(KeyCode.Q) || keyPressed.contains(KeyCode.LEFT)) {
			bill.getChrac().animation("RunLeft");
			oldAnim = "RunLeft";
			removeImages("Left");
		}
	}

	public void removeImages(String direction) {
		switch (direction) {
		case "Right":
			for (int i = 0; i < floor.getChildren().size(); i++) {

				floor.getChildren().get(i).relocate(
						floor.getChildren().get(i).getLayoutX() - bill.getChrac().getSpeed(),
						floor.getChildren().get(i).getLayoutY());
				floor.getChildren().removeIf(n-> n.getLayoutX()<-32);
//				if (floor.getChildren().get(i).getLayoutX() < -32) {
//					floor.getChildren().remove(floor.getChildren().get(i));
//				}

			}
			break;

		case "Left":
			for (int i = floor.getChildren().size() - 1; i >= 0; i--) {

				floor.getChildren().get(i).relocate(
						floor.getChildren().get(i).getLayoutX() + bill.getChrac().getSpeed(),
						floor.getChildren().get(i).getLayoutY());
				if (floor.getChildren().get(i).getLayoutX() > 32 * 60) {
					floor.getChildren().remove(floor.getChildren().get(i));
				}
			}
			break;
		}

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
		KeyFrame kf = new KeyFrame(Duration.seconds(0.02083333333), (ev -> {
			actions();
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
