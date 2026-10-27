package pl.piotrtrybala.engine.executor.types;

import pl.piotrtrybala.engine.executor.enums.ExecutorErrorType;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ExecutorError implements ExecutorResult {

    private ExecutorErrorType error;

    public ExecutorError(ExecutorErrorType error) {
        this.error = error;
    }


    @Override
    public byte[] getResult() {
        StringBuilder builder = new StringBuilder();
        builder.append("failed to execute query: ");
        builder.append(this.error.getReason());

        ByteBuffer bBuffer = StandardCharsets.UTF_8.encode(builder.toString());
        return bBuffer.array();
    }
}
