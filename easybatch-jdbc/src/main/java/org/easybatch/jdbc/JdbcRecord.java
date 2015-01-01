/*
 * The MIT License
 *
 *  Copyright (c) 2015, Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy
 *  of this software and associated documentation files (the "Software"), to deal
 *  in the Software without restriction, including without limitation the rights
 *  to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *  copies of the Software, and to permit persons to whom the Software is
 *  furnished to do so, subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in
 *  all copies or substantial portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *  IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *  AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 *  THE SOFTWARE.
 */

package org.easybatch.jdbc;

import org.easybatch.core.api.Record;
import org.easybatch.core.util.GenericRecord;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

/**
 * A {@link Record} implementation that has database row as raw content.
 *
 * @author Mahmoud Ben Hassine (mahmoud@benhassine.fr)
 */
public class JdbcRecord extends GenericRecord<ResultSet> {

    public JdbcRecord(final int number, final ResultSet payload) {
        super(number, payload);
    }

    // override toString because it is used by easy batch engine for logging errors if any
    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("");
        try {
            ResultSetMetaData resultSetMetaData = payload.getMetaData();
            int columnCount = resultSetMetaData.getColumnCount();
            for (int i = 1; i < columnCount + 1; i++) {
                String name = resultSetMetaData.getColumnName(i);
                stringBuilder.append(name).append("=").append(payload.getString(i)).append("|");
            }
        } catch (SQLException e) {
            throw new RuntimeException("An exception occurred during toString of record " + payload, e);
        }
        return stringBuilder.toString();

    }
}
