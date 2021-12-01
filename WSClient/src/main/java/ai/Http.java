package ai;

/**
 * @author liujd65
 * @date 2021/11/9 15:46
 **/
public class Http {
    String method;
    String url;
    String version;
    byte[] bodyData;
    byte[] data;

    StringBuilder sb = new StringBuilder();

    public Http(String url){
        sb.append("GET" + "\u0020");
        sb.append(url + "\u0020");
        sb.append("HTTP/1.1" + "\r\n");
    }

    Http addHeader(String key, String value){
        sb.append(key+":"+value+"\r\n");
        return this;
    }

    Http endHeader(){
        sb.append("\r\n");
        return this;
    }

    Http addBody(byte[] data){
        sb.append("\r\n");
        sb.append(data + "\r\n");
        return this;
    }

    byte[] toBytes(){
        return sb.toString().getBytes();
    }
}
