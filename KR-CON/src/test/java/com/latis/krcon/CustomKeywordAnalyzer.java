package com.latis.krcon;

import java.io.Reader;
import java.util.logging.Filter;

import org.apache.lucene.analysis.KeywordTokenizer;
import org.apache.lucene.analysis.LowerCaseFilter;
import org.apache.lucene.analysis.ReusableAnalyzerBase;
import org.apache.lucene.analysis.StopFilter;
import org.apache.lucene.analysis.TokenFilter;
import org.apache.lucene.analysis.Tokenizer;
import org.apache.lucene.util.Version;

public class CustomKeywordAnalyzer extends ReusableAnalyzerBase {

    private final Version version;

    public CustomKeywordAnalyzer(final Version version) {
        super();
        this.version = version;
    }

    @Override
    protected TokenStreamComponents createComponents(final String fieldName, final Reader reader) {
        final Tokenizer source = new KeywordTokenizer(reader);
        return new TokenStreamComponents(source, new LowerCaseFilter(this.version, source));
    }

    
    
}
