package epicode.u5s3g1.entities.payloads;

import lombok.Getter;

@Getter
public class UserLoginPayload {
	String email;
	String password;
}