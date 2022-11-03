package hr.xmjosic.springbatchdemoapp.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

import static java.nio.charset.StandardCharsets.UTF_8;

@Component
@RequiredArgsConstructor
public class HashProcessor {

  private final ObjectMapper objectMapper;

  public String hashObject(Object o) {
    try {
      final String s = objectMapper.writeValueAsString(o);
      return DigestUtils.sha256Hex(s.getBytes(UTF_8));
    } catch (JsonProcessingException e) {
      throw new IllegalArgumentException(e);
    }
  }
}
