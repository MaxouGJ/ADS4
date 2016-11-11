import java.io.*;

class LookAhead1Reader extends PushbackReader {
    /* Reader class with a lookahead of one character */

    public LookAhead1Reader(Reader r) {
	super(r,1);
    }

    public boolean check(char c)
	throws IOException {
	/* check whether c is the first character */
	int lastread=0;
	lastread=this.read();
	this.unread(lastread);
	return (lastread == c); 
    }

    public void eat(char expected)
	throws ReadException, IOException {
	/* consumes c from the stream, exception    */
	/* when the contents does not start on c.   */
	char found=(char)this.read();
	if (found != expected) {
	    throw new ReadException(expected,found);
	}
    }
}
