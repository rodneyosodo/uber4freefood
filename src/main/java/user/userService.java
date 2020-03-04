package user;

import com.cloudMessaging.cloud.User.Empty;
import com.cloudMessaging.cloud.User.Text;
import com.cloudMessaging.cloud.User.delivery;
import com.cloudMessaging.cloud.messagesGrpc.messagesImplBase;

import io.grpc.stub.StreamObserver;

public class userService extends messagesImplBase{

	
	String texts = "";
	
	
	@Override
	public void messageIn(Empty request, StreamObserver<Text> responseObserver) {
		Text.Builder sent = Text.newBuilder();
	
				sent.setText(texts);
				
		
		responseObserver.onNext(sent.build());
		responseObserver.onCompleted();
	}

	
	@Override
	public void send(Text request, StreamObserver<delivery> responseObserver) {
		
		String text = request.getText();
		
		
		texts = text;
		
		delivery.Builder response = delivery.newBuilder();
		response.setResponseCode("delivered");
		
		responseObserver.onNext(response.build());
		responseObserver.onCompleted();
	}
	

}
