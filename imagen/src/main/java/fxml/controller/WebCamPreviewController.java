package fxml.controller;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayDeque;
import java.util.List;
import java.util.ResourceBundle;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.util.jh.JHGrayFilter;

import javafx.application.Platform;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;

@SuppressWarnings("restriction")
public class WebCamPreviewController implements Initializable {

	@FXML
	Button btnStartCamera;
	@FXML
	Button btnStopCamera;
	@FXML
	Button btnDisposeCamera;
	@FXML
	ComboBox<WebCamInfo> cbCameraOptions;
	@FXML
	BorderPane bpWebCamPaneHolder;
	@FXML
	FlowPane fpBottomPane;
	@FXML
	ImageView imgWebCamCapturedImage;

	@FXML
	ImageView imgWebCamCapturedImage2;
	@FXML
	ImageView imgWebCamCapturedImage3;

	private BufferedImage grabbedImage;
	private BufferedImage bwImage;
	// private WebcamPanel selWebCamPanel = null;
	private Webcam selWebCam = null;

	private Webcam webcamDefault = Webcam.getDefault();

	private boolean stopCamera = false;
	private ObjectProperty<Image> imageProperty = new SimpleObjectProperty<Image>();
	private ObjectProperty<Image> imageProperty2 = new SimpleObjectProperty<Image>();

	private String cameraListPromptText = "Seleccion Camara";

	// @Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		fpBottomPane.setDisable(true);
		ObservableList<WebCamInfo> options = FXCollections.observableArrayList();
		int webCamCounter = 0;
		for (Webcam webcam : Webcam.getWebcams()) {
			WebCamInfo webCamInfo = new WebCamInfo();
			webCamInfo.setWebCamIndex(webCamCounter);
			webCamInfo.setWebCamName(webcam.getName());
			webcam.setViewSize(new Dimension(640, 480));
			options.add(webCamInfo);
			webCamCounter++;
		}
		cbCameraOptions.setItems(options);
		// cbCameraOptions.getSelectionModel().select(0);
		// initializeWebCam(webCamIndex);
		cbCameraOptions.setPromptText(cameraListPromptText);
		cbCameraOptions.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<WebCamInfo>() {

			// @Override
			public void changed(ObservableValue<? extends WebCamInfo> arg0, WebCamInfo arg1, WebCamInfo arg2) {
				if (arg2 != null) {

					System.out.println(
							"WebCam Index: " + arg2.getWebCamIndex() + ": WebCam Name:" + arg2.getWebCamName());
					initializeWebCam(arg2.getWebCamIndex());
				}
			}
		});
		Platform.runLater(new Runnable() {

			// @Override
			public void run() {

				setImageViewSize();

			}
		});

	}

	protected void setImageViewSize() {

		System.out.println("640x480");
		double height = 1000; // bpWebCamPaneHolder.getHeight();
		double width = 1000; // bpWebCamPaneHolder.getWidth();
		imgWebCamCapturedImage.setFitHeight(height);
		imgWebCamCapturedImage.setFitWidth(width);
		imgWebCamCapturedImage.prefHeight(height);
		imgWebCamCapturedImage.prefWidth(width);
		imgWebCamCapturedImage.setPreserveRatio(true);

		imgWebCamCapturedImage2.setFitHeight(height);
		imgWebCamCapturedImage2.setFitWidth(width);
		imgWebCamCapturedImage2.prefHeight(height);
		imgWebCamCapturedImage2.prefWidth(width);
		imgWebCamCapturedImage2.setPreserveRatio(true);

		imgWebCamCapturedImage3.setFitHeight(height);
		imgWebCamCapturedImage3.setFitWidth(width);
		imgWebCamCapturedImage3.prefHeight(height);
		imgWebCamCapturedImage3.prefWidth(width);
		imgWebCamCapturedImage3.setPreserveRatio(true);
	}

	protected void initializeWebCam(final int webCamIndex) {

		Task<Void> webCamIntilizer = new Task<Void>() {

			@Override
			protected Void call() throws Exception {

				if (selWebCam == null) {
					selWebCam = Webcam.getWebcams().get(webCamIndex);
					selWebCam.open();
				} else {
					closeCamera();
					selWebCam = Webcam.getWebcams().get(webCamIndex);
					selWebCam.open();

				}
				startWebCamStream();
				return null;
			}

		};

		new Thread(webCamIntilizer).start();
		fpBottomPane.setDisable(false);
		btnStartCamera.setDisable(true);
	}

	protected void startWebCamStream() {

		stopCamera = false;
		Task<Void> task = new Task<Void>() {

			@Override
			protected Void call() throws Exception {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd_hh_mm_ss_SSS");
//				ByteArrayOutputStream baos;
//				InputStream bais;
//				ToGray tg = new ToGray();

				ArrayDeque<BufferedImage>imagenes = new ArrayDeque<BufferedImage>(1000);
				
				while (!stopCamera) {
					try {
						if ((grabbedImage = webcamDefault.getImage()) != null) {
							bwImage = ToGray.convertBufferedImage(grabbedImage, imagenes);

//							String file = "imgs/img_" + sdf.format(new Date()) + ".jpg";
//							ImageIO.write(grabbedImage, "jpg", new File(file));
							Platform.runLater(new Runnable() {
								// @Override
								public void run() {
									final Image mainiamge = SwingFXUtils.toFXImage(grabbedImage, null);
									final Image mainiamge2 = SwingFXUtils.toFXImage(bwImage, null);
//									Color result = mainiamge.getPixelReader().getColor(0, 0);
//									System.out.println("result: " + result);
									imageProperty.set(mainiamge);
									imageProperty2.set(mainiamge2);
									
								}
							});

//							grabbedImage.flush();

						}
					} catch (Exception e) {
					} finally {

					}

				}

				return null;

			}

		};
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
		imgWebCamCapturedImage.imageProperty().bind(imageProperty);
		imgWebCamCapturedImage2.imageProperty().bind(imageProperty2);
		imgWebCamCapturedImage3.imageProperty().bind(imageProperty2);

	}

	private void closeCamera() {
		if (webcamDefault != null) {
			webcamDefault.close();
		}
	}

	public void stopCamera(ActionEvent event) {
		stopCamera = true;
		btnStartCamera.setDisable(false);
		btnStopCamera.setDisable(true);
	}

	public void startCamera(ActionEvent event) {
		stopCamera = false;
		startWebCamStream();

		btnStartCamera.setDisable(true);
		btnStopCamera.setDisable(false);

	}

	private static final JHGrayFilter GRAY = new JHGrayFilter();

	public BufferedImage transform(BufferedImage image) {
		return GRAY.filter(image, null);
	}

	public void disposeCamera(ActionEvent event) {
		stopCamera = true;
		closeCamera();
		Webcam.shutdown();
		btnStopCamera.setDisable(true);
		btnStartCamera.setDisable(true);
	}

	class WebCamInfo {
		private String webCamName;
		private int webCamIndex;

		public String getWebCamName() {
			return webCamName;
		}

		public void setWebCamName(String webCamName) {
			this.webCamName = webCamName;
		}

		public int getWebCamIndex() {
			return webCamIndex;
		}

		public void setWebCamIndex(int webCamIndex) {
			this.webCamIndex = webCamIndex;
		}

		@Override
		public String toString() {
			return webCamName;
		}

	}
}
