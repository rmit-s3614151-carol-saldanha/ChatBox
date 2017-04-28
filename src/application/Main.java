package application;
	
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;


public class Main extends Application {
	
	public enum Direction  {
		UP, DOWN, LEFT, RIGHT
	}
	
	public static final int BLOCK_SIZE = 20;
	public static final int APP_W = 40*BLOCK_SIZE;
	public static final int APP_H = 30*BLOCK_SIZE;
	
	private Direction direction = Direction.RIGHT;
	private Direction direction2 = Direction.LEFT;
	private boolean running = false;
	private boolean foodLoop=true;
	private Timeline timeline = new Timeline(); 
	private ObservableList<Node> snake;
	private ObservableList<Node> snake2; //SECOND SNAKE
	private ObservableList<Node> obstacle;
	private int foodCount=0;
	private int obstacleCount=20000;
	
	private int alternateCount=1;
	private int alternateCount2=1;
	Label label = new Label("");
	Label black = new Label("PYTHON: ");
	Label green = new Label("VIPER: ");
	private int countBlack = 0;
	private int countGreen = 0;
	private Parent createContent() {
		Pane root = new Pane();
		
		root.setPrefSize(APP_W, APP_H);
		
		Group snakeBody = new Group();
		snake = snakeBody.getChildren();
		
		Group snakeBody2 = new Group(); //SECOND BODY
		snake2 = snakeBody2.getChildren();
		
		Group obstacleBody = new Group(); 
		obstacle = obstacleBody.getChildren();
		
		Circle food = new Circle((int)BLOCK_SIZE/2);
		food.setFill(Color.MAROON);
		
		food.setTranslateY((int)(Math.random() * (APP_H - BLOCK_SIZE/2))/BLOCK_SIZE*BLOCK_SIZE +BLOCK_SIZE/2);
		food.setTranslateX((int)(Math.random() * (APP_W - BLOCK_SIZE/2))/BLOCK_SIZE*BLOCK_SIZE + BLOCK_SIZE/2);

		KeyFrame frame = new KeyFrame(Duration.seconds(0.08), event -> {
			if (!running)
				return;

			boolean toRemove = snake.size() > 1;
			boolean toRemove2 = snake2.size() > 1;

			Node tail = toRemove ? snake.remove(snake.size()-1) : snake.get(0);
			Node tail2 = toRemove2 ? snake2.remove(snake2.size()-1) : snake2.get(0);
			
			double tailX = tail.getTranslateX();
			double tailY = tail.getTranslateY();
			
			double tailX2 = tail2.getTranslateX();
			double tailY2 = tail2.getTranslateY();
			
			
			switch (direction) {
			
			case UP: 
				tail.setTranslateX(snake.get(0).getTranslateX());
				tail.setTranslateY(snake.get(0).getTranslateY()-BLOCK_SIZE);
				break;
			case DOWN: 
				tail.setTranslateX(snake.get(0).getTranslateX());
				tail.setTranslateY(snake.get(0).getTranslateY()+BLOCK_SIZE);
				break;
			case LEFT: 
				tail.setTranslateX(snake.get(0).getTranslateX()-BLOCK_SIZE);
				tail.setTranslateY(snake.get(0).getTranslateY());
				break;
			case RIGHT: 
				tail.setTranslateX(snake.get(0).getTranslateX()+BLOCK_SIZE);
				tail.setTranslateY(snake.get(0).getTranslateY());
				break;
						
			}
			
			switch (direction2) {
			
			case UP: 
				tail2.setTranslateX(snake2.get(0).getTranslateX());
				tail2.setTranslateY(snake2.get(0).getTranslateY()-BLOCK_SIZE);
				break;
			case DOWN: 
				tail2.setTranslateX(snake2.get(0).getTranslateX());
				tail2.setTranslateY(snake2.get(0).getTranslateY()+BLOCK_SIZE);
				break;
			case LEFT: 
				tail2.setTranslateX(snake2.get(0).getTranslateX()-BLOCK_SIZE);
				tail2.setTranslateY(snake2.get(0).getTranslateY());
				break;
			case RIGHT: 
				tail2.setTranslateX(snake2.get(0).getTranslateX()+BLOCK_SIZE);
				tail2.setTranslateY(snake2.get(0).getTranslateY());
				break;
						
			}


			
			if (toRemove)
				snake.add(0,tail);
			
			if (toRemove2)
				snake2.add(0,tail2);
		
			//collision detection
			
			for (Node rect : snake) {
				if (rect != tail && tail.getTranslateX() == rect.getTranslateX() && 
						tail.getTranslateY() == rect.getTranslateY())
				{
					restartGame();
					break;
				}
			}
			
			for (Node rect : obstacle) {
				if (rect != tail && tail.getTranslateX() == rect.getTranslateX() && 
						tail.getTranslateY() == rect.getTranslateY())
				{
					restartGame();
					break;
				}
			}
			
			if (tail.getTranslateX() < 0 || tail.getTranslateX() >= APP_W 
					|| tail.getTranslateY() < 0 || tail.getTranslateY() >= APP_H ) {
				restartGame();
			}
			
			if(tail.getTranslateX() + BLOCK_SIZE/2 == food.getTranslateX() 
					&& tail.getTranslateY() + BLOCK_SIZE/2 == food.getTranslateY() ) {
				
				while(true)
				{
				foodLoop =true;

				food.setTranslateX((int)(Math.random() * (APP_W - BLOCK_SIZE))/BLOCK_SIZE*BLOCK_SIZE +BLOCK_SIZE/2);
				food.setTranslateY((int)(Math.random() * (APP_H - BLOCK_SIZE))/BLOCK_SIZE*BLOCK_SIZE +BLOCK_SIZE/2);
				for(Node rect : obstacle)
					if (food.getTranslateX() == rect.getTranslateX() + BLOCK_SIZE/2  && 
					food.getTranslateY() == rect.getTranslateY() + BLOCK_SIZE/2 )
						{
							foodLoop = false;
						}
				
				if(!foodLoop) continue;
				
				for(Node rect : snake)
					if (food.getTranslateX() == rect.getTranslateX() + BLOCK_SIZE/2  && 
					food.getTranslateY() == rect.getTranslateY() + BLOCK_SIZE/2 )
						{
						foodLoop = false;

						}
				
				if(!foodLoop) continue;

				
				for(Node rect : snake2)
					if (food.getTranslateX() == rect.getTranslateX() + BLOCK_SIZE/2  && 
					food.getTranslateY() == rect.getTranslateY() + BLOCK_SIZE/2 )
						{
						foodLoop = false;
						}
				if(!foodLoop) continue;

				
				break;
				}
				
				Rectangle rect = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
				rect.setTranslateX(tailX);
				rect.setTranslateY(tailY);
				
				
				if(alternateCount == 1)
					rect.setFill(Color.web("0xFF8300"));
					else
					rect.setFill(Color.BLACK);

				alternateCount*=-1;
				
				snake.add(rect);
				countBlack=countBlack + snake.size();
				black.setText("PYTHON : "+countBlack);
				
				if(countBlack>=500) {
					gameOver();
				}
				
				foodCount++;

				
			}
			
			
			for (Node rect : snake2) {
				if (rect != tail2 && tail2.getTranslateX() == rect.getTranslateX() && 
						tail2.getTranslateY() == rect.getTranslateY())
				{
					restartGame2();
					break;
				}
			}
			
			for (Node rect : obstacle) {
				if (rect != tail2 && tail2.getTranslateX() == rect.getTranslateX() && 
						tail2.getTranslateY() == rect.getTranslateY())
				{
					restartGame2();
					break;
				}
			}
			
			if (tail2.getTranslateX() < 0 || tail2.getTranslateX() >= APP_W 
					|| tail2.getTranslateY() < 0 || tail2.getTranslateY() >= APP_H ) {
				restartGame2();
			}
			
			if(tail2.getTranslateX() + BLOCK_SIZE/2 == food.getTranslateX() 
					&& tail2.getTranslateY()+BLOCK_SIZE/2 == food.getTranslateY() ) {
				
				
				while(true)
				{
				foodLoop =true;

				food.setTranslateX((int)(Math.random() * (APP_W - BLOCK_SIZE))/BLOCK_SIZE*BLOCK_SIZE +BLOCK_SIZE/2);
				food.setTranslateY((int)(Math.random() * (APP_H - BLOCK_SIZE))/BLOCK_SIZE*BLOCK_SIZE +BLOCK_SIZE/2);
				for(Node rect : obstacle)
					if (food.getTranslateX() == rect.getTranslateX() + BLOCK_SIZE/2  && 
					food.getTranslateY() == rect.getTranslateY() + BLOCK_SIZE/2 )
						{
							foodLoop = false;
						}
				
				if(!foodLoop) continue;
				
				for(Node rect : snake)
					if (food.getTranslateX() == rect.getTranslateX() + BLOCK_SIZE/2  && 
					food.getTranslateY() == rect.getTranslateY() + BLOCK_SIZE/2 )
						{
						foodLoop = false;
;
						}
				
				if(!foodLoop) continue;

				
				for(Node rect : snake2)
					if (food.getTranslateX() == rect.getTranslateX() + BLOCK_SIZE/2  && 
					food.getTranslateY() == rect.getTranslateY() + BLOCK_SIZE/2 )
						{
						foodLoop = false;
						}
				if(!foodLoop) continue;

				
				break;
				}
				Rectangle rect = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
				rect.setTranslateX(tailX2);
				rect.setTranslateY(tailY2);
				
				if(alternateCount2==-1)
				rect.setFill(Color.DARKGREEN);
				else
				rect.setFill(Color.GOLD);
				
				alternateCount2*=-1;
				
				snake2.add(rect);
				
				countGreen=countGreen + snake2.size();
				green.setText("VIPER : "+countGreen);
				if(countGreen>=500) {
					gameOver();
				}
				
				foodCount++;
			}
			
			//OBSTACLE MANAGEMENT
			if(foodCount==obstacleCount ) {
				changeObstacle();
				obstacleCount+=6;
			}
			
		});
		
		
		timeline.getKeyFrames().add(frame);
		timeline.setCycleCount(Timeline.INDEFINITE);
		root.setBackground(new Background(new BackgroundFill(Color.web("0x5F9263") , null, null)));
		root.getChildren().addAll(food,snakeBody,snakeBody2,label, black, green,obstacleBody);
		return root;
	}
	
	private void changeObstacle() {
		
	Rectangle obs = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);

	obs.setTranslateX((int)(Math.random() * (APP_W - BLOCK_SIZE))/BLOCK_SIZE*BLOCK_SIZE);
	obs.setTranslateY((int)(BLOCK_SIZE+Math.random() * (APP_H - 2*BLOCK_SIZE))/BLOCK_SIZE*BLOCK_SIZE);
	
	obs.setFill(Color.RED);
	obs.setStroke(Color.BLACK);
	obs.setStrokeWidth(3);
	
	obstacle.add(obs);
		
	}
	
	private void customObstacle() {
		

			for(int i = 13; i <=20; i++){
				Rectangle obs = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
				Rectangle obs2 = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);

				obs.setTranslateX((int)( i*BLOCK_SIZE));
				obs.setTranslateY(5*BLOCK_SIZE);
				
				obs.setFill(Color.RED);
				obs.setStroke(Color.BLACK);
				obs.setStrokeWidth(3);
				
				obstacle.add(obs);
				
				obs2.setTranslateX((int)( i*BLOCK_SIZE));
				obs2.setTranslateY(15*BLOCK_SIZE);
				
				obs2.setFill(Color.RED);
				obs2.setStroke(Color.BLACK);
				obs2.setStrokeWidth(3);
				
				obstacle.add(obs2);
			}
			
			for(int i = 5; i <=15; i++){
				Rectangle obs = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
				Rectangle obs2 = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);

				if(i>8 && i < 12)
				{
					continue;
				}
				obs.setTranslateY((int)( i*BLOCK_SIZE));
				obs.setTranslateX(20*BLOCK_SIZE);
				
				obs.setFill(Color.RED);
				obs.setStroke(Color.BLACK);
				obs.setStrokeWidth(3);
				
				obstacle.add(obs);
				
				obs2.setTranslateY((int)( i*BLOCK_SIZE));
				obs2.setTranslateX(10*BLOCK_SIZE);
				
				obs2.setFill(Color.RED);
				obs2.setStroke(Color.BLACK);
				obs2.setStrokeWidth(3);
				
				obstacle.add(obs2);
			}
			
		}

	private void gameOver() {
		label.setTranslateX((int)APP_W/2);
		label.setTranslateY((int)APP_H/2);
		label.setFont(new Font("Cambria", 40));

		if(countGreen>countBlack)
			{label.setTextFill(Color.BLACK);
			label.setText("VIPER WINS!");}
		else
			{label.setTextFill(Color.BLACK);
			label.setText("PYTHON WINS!");}
		
		stopGame();
		stopGame2();

	}

	private void restartGame() {

		stopGame();
		startGame();
	}
	
	private void restartGame2() {

		stopGame2();
		startGame2();
	}
	
	
	private void stopGame() {
		
		running = false;
		timeline.stop();
		snake.clear();
	}
	
	private void stopGame2() {
		
		running = false;
		timeline.stop();
		snake2.clear();
	}
	
private void startGame() {
		direction = Direction.RIGHT;
		black.setTranslateX(0);
		black.setTranslateY(APP_H-30);
		green.setTranslateX(APP_W-150);
		green.setTranslateY(APP_H-30);
		black.setFont(new Font("Cambria", 20));
		green.setFont(new Font("Cambria", 20));

		customObstacle();
		Rectangle head = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
		head.setFill(Color.BLACK);

		snake.add(head);
		timeline.play();
		running = true;	
		}

private void startGame2() {
	direction2 = Direction.LEFT;
	
	Rectangle head2 = new Rectangle(BLOCK_SIZE, BLOCK_SIZE);
	

	head2.setTranslateX((int)((APP_W - BLOCK_SIZE))/BLOCK_SIZE*BLOCK_SIZE );
	head2.setTranslateY(0);
	head2.setFill(Color.DARKGREEN);
	snake2.add(head2);
	timeline.play();
	running = true;	
	}
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		Scene scene = new Scene(createContent());
		
		
		final BooleanProperty wPressed = new SimpleBooleanProperty(false);
		final BooleanProperty aPressed = new SimpleBooleanProperty(false);
		final BooleanProperty sPressed = new SimpleBooleanProperty(false);
		final BooleanProperty dPressed = new SimpleBooleanProperty(false);
		
		final BooleanProperty upPressed = new SimpleBooleanProperty(false);
		final BooleanProperty downPressed = new SimpleBooleanProperty(false);
		final BooleanProperty leftPressed = new SimpleBooleanProperty(false);
		final BooleanProperty rightPressed = new SimpleBooleanProperty(false);
		
		
		final BooleanBinding wAndUpPressed = wPressed.and(upPressed);
		final BooleanBinding wAndDownPressed = wPressed.and(downPressed);
		final BooleanBinding wAndLeftPressed = wPressed.and(leftPressed);
		final BooleanBinding wAndRightPressed = wPressed.and(rightPressed);
		
		final BooleanBinding aAndUpPressed = aPressed.and(upPressed);
		final BooleanBinding aAndDownPressed = aPressed.and(downPressed);
		final BooleanBinding aAndLeftPressed = aPressed.and(leftPressed);
		final BooleanBinding aAndRightPressed = aPressed.and(rightPressed);
		
		final BooleanBinding sAndUpPressed = sPressed.and(upPressed);
		final BooleanBinding sAndDownPressed = sPressed.and(downPressed);
		final BooleanBinding sAndLeftPressed = sPressed.and(leftPressed);
		final BooleanBinding sAndRightPressed = sPressed.and(rightPressed);
		
		final BooleanBinding dAndUpPressed = dPressed.and(upPressed);
		final BooleanBinding dAndDownPressed = dPressed.and(downPressed);
		final BooleanBinding dAndLeftPressed = dPressed.and(leftPressed);
		final BooleanBinding dAndRightPressed = dPressed.and(rightPressed);
		
	
		wPressed.addListener(new ChangeListener<Boolean>() {
			   
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if(direction != Direction.DOWN) direction = Direction.UP;
					
				}
			});
		
		aPressed.addListener(new ChangeListener<Boolean>() {
			   
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(direction != Direction.RIGHT) direction = Direction.LEFT;
				
			}
		});
	
		sPressed.addListener(new ChangeListener<Boolean>() {
			   
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(direction != Direction.UP) direction = Direction.DOWN;
				
			}
		});
	
		dPressed.addListener(new ChangeListener<Boolean>() {
			   
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(direction != Direction.LEFT) direction = Direction.RIGHT;
				
			}
		});
	
		
		upPressed.addListener(new ChangeListener<Boolean>() {
			   
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(direction2 != Direction.DOWN) direction2 = Direction.UP;
				
			}
		});
	
		downPressed.addListener(new ChangeListener<Boolean>() {
			   
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(direction2 != Direction.UP) direction2 = Direction.DOWN;
				
			}
		});
	
		leftPressed.addListener(new ChangeListener<Boolean>() {
			   
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(direction2 != Direction.RIGHT) direction2 = Direction.LEFT;
				
			}
		});
	
		rightPressed.addListener(new ChangeListener<Boolean>() {
			   
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(direction2 != Direction.LEFT) direction2 = Direction.RIGHT;
				
			}
		});
	
//W combinations
		// How to respond to both keys pressed together:
		wAndUpPressed.addListener(new ChangeListener<Boolean>() {
		   
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(direction != Direction.DOWN) direction = Direction.UP;
				if(direction2 != Direction.DOWN) direction2 = Direction.UP;
				
			}
		});

		// How to respond to both keys pressed together:
				wAndDownPressed.addListener(new ChangeListener<Boolean>() {
				   
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if(direction != Direction.DOWN) direction = Direction.UP;
						if(direction2 != Direction.UP) direction2 = Direction.DOWN;
						
					}
				});
		// How to respond to both keys pressed together:
				wAndRightPressed.addListener(new ChangeListener<Boolean>() {
					   
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if(direction != Direction.DOWN) direction = Direction.UP;
						if(direction2 != Direction.LEFT) direction2 = Direction.RIGHT;
						
					}
				});
		// How to respond to both keys pressed together:
				wAndLeftPressed.addListener(new ChangeListener<Boolean>() {
				   
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if(direction != Direction.DOWN) direction = Direction.UP;
						if(direction2 != Direction.RIGHT) direction2 = Direction.LEFT;
						
					}
				});
				
//A Combinations
		
		// How to respond to both keys pressed together:
		aAndUpPressed.addListener(new ChangeListener<Boolean>() {
		   
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(direction != Direction.RIGHT) direction = Direction.LEFT;
				if(direction2 != Direction.DOWN) direction2 = Direction.UP;
				
			}
		});

		// How to respond to both keys pressed together:
				aAndDownPressed.addListener(new ChangeListener<Boolean>() {
				   
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if(direction != Direction.RIGHT) direction = Direction.LEFT;
						if(direction2 != Direction.UP) direction2 = Direction.DOWN;
						
					}
				});
		// How to respond to both keys pressed together:
				aAndRightPressed.addListener(new ChangeListener<Boolean>() {
					   
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if(direction != Direction.RIGHT) direction = Direction.LEFT;
						if(direction2 != Direction.LEFT) direction2 = Direction.RIGHT;
						
					}
				});
		// How to respond to both keys pressed together:
				aAndLeftPressed.addListener(new ChangeListener<Boolean>() {
				   
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if(direction != Direction.RIGHT) direction = Direction.LEFT;
						if(direction2 != Direction.RIGHT) direction2 = Direction.LEFT;
						
					}
				});
				
//S combinations

		
		// How to respond to both keys pressed together:
		sAndUpPressed.addListener(new ChangeListener<Boolean>() {
		   
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(direction != Direction.UP) direction = Direction.DOWN;
				if(direction2 != Direction.DOWN) direction2 = Direction.UP;
				
			}
		});

		// How to respond to both keys pressed together:
				sAndDownPressed.addListener(new ChangeListener<Boolean>() {
				   
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if(direction != Direction.UP) direction = Direction.DOWN;
						if(direction2 != Direction.UP) direction2 = Direction.DOWN;
						
					}
				});
		// How to respond to both keys pressed together:
				sAndRightPressed.addListener(new ChangeListener<Boolean>() {
					   
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if(direction != Direction.UP) direction = Direction.DOWN;
						if(direction2 != Direction.LEFT) direction2 = Direction.RIGHT;
						
					}
				});
		// How to respond to both keys pressed together:
				sAndLeftPressed.addListener(new ChangeListener<Boolean>() {
				   
					@Override
					public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
						if(direction != Direction.UP) direction = Direction.DOWN;
						if(direction2 != Direction.RIGHT) direction2 = Direction.LEFT;
						
					}
				});
				
//D combinations


// How to respond to both keys pressed together:
dAndUpPressed.addListener(new ChangeListener<Boolean>() {
   
	@Override
	public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
		if(direction != Direction.LEFT) direction = Direction.RIGHT;
		if(direction2 != Direction.DOWN) direction2 = Direction.UP;
		
	}
});

// How to respond to both keys pressed together:
		dAndDownPressed.addListener(new ChangeListener<Boolean>() {
		   
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(direction != Direction.LEFT) direction = Direction.RIGHT;
				if(direction2 != Direction.UP) direction2 = Direction.DOWN;
				
			}
		});
// How to respond to both keys pressed together:
		dAndRightPressed.addListener(new ChangeListener<Boolean>() {
			   
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(direction != Direction.LEFT) direction = Direction.RIGHT;
				if(direction2 != Direction.LEFT) direction2 = Direction.RIGHT;
				
			}
		});
// How to respond to both keys pressed together:
		dAndLeftPressed.addListener(new ChangeListener<Boolean>() {
		   
			@Override
			public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
				if(direction != Direction.LEFT) direction = Direction.RIGHT;
				if(direction2 != Direction.RIGHT) direction2 = Direction.LEFT;
				
			}
		});
		
		// Wire up properties to key events:
				scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				    @Override
				    public void handle(KeyEvent ke) {
				        if (ke.getCode() == KeyCode.W) {
				            wPressed.set(true);
				        } else if (ke.getCode() == KeyCode.A) {
				            aPressed.set(true);
				        }else if (ke.getCode() == KeyCode.S) {
				            sPressed.set(true);
				        }else if (ke.getCode() == KeyCode.D) {
				            dPressed.set(true);
				        }else if (ke.getCode() == KeyCode.UP) {
				            upPressed.set(true);
				        }else if (ke.getCode() == KeyCode.DOWN) {
				            downPressed.set(true);
				        }else if (ke.getCode() == KeyCode.LEFT) {
				            leftPressed.set(true);
				        }else if (ke.getCode() == KeyCode.RIGHT) {
				            rightPressed.set(true);
				        }
				    }
				});
				    
		 // Wire up properties to key events:
			scene.setOnKeyReleased(new EventHandler<KeyEvent>() {
			    @Override
			    public void handle(KeyEvent ke) {
			        if (ke.getCode() == KeyCode.W) {
			            wPressed.set(false);
			        } else if (ke.getCode() == KeyCode.A) {
			            aPressed.set(false);
			        }else if (ke.getCode() == KeyCode.S) {
			            sPressed.set(false);
			        }else if (ke.getCode() == KeyCode.D) {
			            dPressed.set(false);
			        }else if (ke.getCode() == KeyCode.UP) {
			            upPressed.set(false);
			        }else if (ke.getCode() == KeyCode.DOWN) {
			            downPressed.set(false);
			        }else if (ke.getCode() == KeyCode.LEFT) {
			            leftPressed.set(false);
			        }else if (ke.getCode() == KeyCode.RIGHT) {
			            rightPressed.set(false);
			        }
			    }
		
			});
		
		primaryStage.setTitle("Snake");
		primaryStage.setScene(scene);
		primaryStage.show();
		startGame();
		startGame2();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
