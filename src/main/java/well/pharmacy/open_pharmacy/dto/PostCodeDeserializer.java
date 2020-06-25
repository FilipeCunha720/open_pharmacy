package well.pharmacy.open_pharmacy.dto;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import java.io.IOException;

public class PostCodeDeserializer extends StdDeserializer<PostCode> {

    public PostCodeDeserializer() {
        this(null);
    }

    public PostCodeDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public PostCode deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException {
        JsonNode node = jp.getCodec().readTree(jp);
        return new PostCode(node.asText());
    }

}
