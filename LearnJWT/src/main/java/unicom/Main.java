package unicom;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

public class Main {
    public static void main(String[] args) {
        JwtBuilder builder= Jwts.builder().setId("888") .setSubject("小白")
                .setIssuedAt(new Date())
                .signWith(SignatureAlgorithm.HS256,"abcde");
        String token = builder.compact();


        Claims claims = Jwts.parser().setSigningKey("abcde").parseClaimsJws(token).getBody();

        System.out.println(token);
        System.out.println("id:"+claims.getId());
        System.out.println("subject:"+claims.getSubject());
        System.out.println("IssuedAt:"+claims.getIssuedAt());
        Jwts.parser().setSigningKey("abcde").parseClaimsJws(token);


        // swagger docker
    }
}
