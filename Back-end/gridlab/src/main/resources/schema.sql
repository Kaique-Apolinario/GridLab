CREATE TABLE T_RC_FILEENTITY (
  nr_idFile         INTEGER NOT NULL,
  txt_fileName      VARCHAR(50) NOT NULL,
  txt_fileUrl       VARCHAR(100) NOT NULL,
  nr_size           INTEGER NOT NULL,
  txt_contentType   VARCHAR(15) NOT NULL,
  txt_sheetData     BYTEA NOT NULL,
  PRIMARY KEY (nr_idFile)
);

ALTER TABLE T_RC_FILEENTITY ADD dt_timeNDate DATE;
ALTER TABLE dt_timeNDate MODIFY TIMESTAMP WITHOUT TIME ZONE; 