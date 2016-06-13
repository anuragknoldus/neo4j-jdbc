/*
 * Copyright (c) 2016 LARUS Business Automation [http://www.larus-ba.it]
 * <p>
 * This file is part of the "LARUS Integration Framework for Neo4j".
 * <p>
 * The "LARUS Integration Framework for Neo4j" is licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * <p>
 * Created on 18/02/16
 */
package org.neo4j.jdbc.bolt;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.neo4j.driver.v1.StatementResult;
import org.neo4j.jdbc.ResultSetMetaData;
import org.neo4j.jdbc.bolt.data.ResultSetData;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

/**
 * @author AgileLARUS
 * @since 3.0.0
 */
public class BoltResultSetMetaDataTest {

	@Rule public ExpectedException expectedEx = ExpectedException.none();

	@BeforeClass public static void initialize() {
		ResultSetData.initialize();
	}

	/*------------------------------*/
	/*        getColumnCount        */
	/*------------------------------*/

	@Test public void getColumnsCountShouldReturnCorrectNumberEmpty() throws SQLException {
		StatementResult resultIterator = ResultSetData.buildResultCursor(ResultSetData.KEYS_RECORD_LIST_EMPTY, ResultSetData.RECORD_LIST_EMPTY);
		ResultSetMetaData resultSet = new BoltResultSetMetaData(resultIterator, resultIterator.keys());

		assertEquals(0, resultSet.getColumnCount());
	}

	@Test public void getColumnsCountShouldReturnCorrectNumberMoreElements() throws SQLException {
		StatementResult resultCursor = ResultSetData.buildResultCursor(ResultSetData.KEYS_RECORD_LIST_MORE_ELEMENTS, ResultSetData.RECORD_LIST_MORE_ELEMENTS);
		ResultSetMetaData resultSet = new BoltResultSetMetaData(resultCursor, resultCursor.keys());

		assertEquals(2, resultSet.getColumnCount());
	}

	@Test public void getColumnsCountShouldThrowExceptionWhenCursorNull() throws SQLException {
		expectedEx.expect(SQLException.class);

		ResultSetMetaData resultSet = new BoltResultSetMetaData(null, null);

		resultSet.getColumnCount();
	}

	/*------------------------------*/
	/*         getColumnName        */
	/*------------------------------*/

	@Test public void getColumnNameShouldReturnCorrectColumnName() throws SQLException {
		StatementResult resultCursor = ResultSetData.buildResultCursor(ResultSetData.KEYS_RECORD_LIST_MORE_ELEMENTS, ResultSetData.RECORD_LIST_MORE_ELEMENTS);
		ResultSetMetaData resultSet = new BoltResultSetMetaData(resultCursor, resultCursor.keys());

		assertEquals("columnA", resultSet.getColumnName(1));
	}

	@Test public void getColumnNameShouldThrowExceptionWhenEmptyCursor() throws SQLException {
		expectedEx.expect(SQLException.class);

		StatementResult resultCursor = ResultSetData.buildResultCursor(ResultSetData.KEYS_RECORD_LIST_EMPTY, ResultSetData.RECORD_LIST_EMPTY);
		ResultSetMetaData resultSetMetaData = new BoltResultSetMetaData(resultCursor, resultCursor.keys());

		resultSetMetaData.getColumnName(1);
	}

	@Test public void getColumnNameShouldThrowExceptionWhenColumnOutOfRange() throws SQLException {
		expectedEx.expect(SQLException.class);

		StatementResult resultCursor = ResultSetData.buildResultCursor(ResultSetData.KEYS_RECORD_LIST_EMPTY, ResultSetData.RECORD_LIST_EMPTY);
		ResultSetMetaData resultSet = new BoltResultSetMetaData(resultCursor, resultCursor.keys());

		resultSet.getColumnName(99);
	}

	@Test public void getColumnNameShouldThrowExceptionIfCursorNull() throws SQLException {
		expectedEx.expect(SQLException.class);

		ResultSetMetaData resultSet = new BoltResultSetMetaData(null, null);

		resultSet.getColumnName(1);
	}

	/*------------------------------*/
	/*         getColumnLabel        */
	/*------------------------------*/

	@Test public void getColumnLabelShouldReturnCorrectColumnName() throws SQLException {
		StatementResult resultCursor = ResultSetData.buildResultCursor(ResultSetData.KEYS_RECORD_LIST_MORE_ELEMENTS, ResultSetData.RECORD_LIST_MORE_ELEMENTS);
		ResultSetMetaData resultSet = new BoltResultSetMetaData(resultCursor, resultCursor.keys());

		assertEquals("columnA", resultSet.getColumnLabel(1));
	}

	@Test public void getColumnLabelShouldThrowExceptionWhenEmptyCursor() throws SQLException {
		expectedEx.expect(SQLException.class);

		StatementResult resultCursor = ResultSetData.buildResultCursor(ResultSetData.KEYS_RECORD_LIST_EMPTY, ResultSetData.RECORD_LIST_EMPTY);
		ResultSetMetaData resultSet = new BoltResultSetMetaData(resultCursor, resultCursor.keys());

		resultSet.getColumnLabel(1);
	}

	@Test public void getColumnLabelShouldThrowExceptionWhenColumnOutOfRange() throws SQLException {
		expectedEx.expect(SQLException.class);

		StatementResult resultCursor = ResultSetData.buildResultCursor(ResultSetData.KEYS_RECORD_LIST_EMPTY, ResultSetData.RECORD_LIST_EMPTY);
		ResultSetMetaData resultSet = new BoltResultSetMetaData(resultCursor, resultCursor.keys());

		resultSet.getColumnLabel(99);
	}

	@Test public void getColumnLabelShouldThrowExceptionIfCursorNull() throws SQLException {
		expectedEx.expect(SQLException.class);

		ResultSetMetaData resultSet = new BoltResultSetMetaData(null, null);

		resultSet.getColumnLabel(1);
	}

	/*------------------------------*/
	/*         getSchemaName        */
	/*------------------------------*/

	@Test public void getSchemaNameShouldReturnDefaultValue() throws SQLException {
		ResultSetMetaData resultSet = new BoltResultSetMetaData(null, null);

		assertEquals("", resultSet.getSchemaName(1));
	}

	/*------------------------------*/
	/*        getCatalogName        */
	/*------------------------------*/

	@Test public void getCatalogNameShouldReturnEmptyString() throws SQLException {
		ResultSetMetaData resultSet = new BoltResultSetMetaData(null, null);

		assertEquals("", resultSet.getCatalogName(1));
	}

}
