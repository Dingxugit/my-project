import React, { useState } from 'react';
import { Modal, Upload, message } from 'antd';
import {
  InboxOutlined,
} from '@ant-design/icons';
import { getAccessToken } from '@/utils/token';
import {importData} from '@/services/ylshh/jykgl/xxtj';

const { Dragger } = Upload;

const ModalImport = (props) => {
  const { modalImportVisible, onSubmit, onCancel } = props;

  const [loading, handleLoading] = useState(false);
  const [fileList, handleFileList] = useState([]);
  const [wenjianlj, handleWenjianlj] = useState('');

  const okHandle = async () => {
    if (!wenjianlj) {
      message.error('请上传Excel格式的数据文件!');
      return
    }

    handleLoading(true)

    const data = await importData(wenjianlj);
    if (data.isSuccess) {
      message.success('上传数据成功!');
      onSubmit();
    } else {
      handleLoading(false)
      message.error(data.message);
    }
  };

  const beforeUpload = (file) => {
    const isLt10M = file.size / 1024 / 1024 < 10;
    if (!isLt10M) {
      message.error('请上传10M内的Excel文件!');
    }

    return isLt10M;
  }

  const handleUploadChange = (info) => {
    let files = [...info.fileList];
    files = files.slice(-1);
    handleFileList(files)

    if (info.file && info.file.response) {
      const data = info.file.response
      handleWenjianlj(data.result)
    }
  }

  return (
    <Modal
      confirmLoading={loading}
      destroyOnClose
      title="上传油料供应充值凭证Excel文件"
      visible={modalImportVisible}
      onOk={okHandle}
      onCancel={onCancel}
    >
      <Dragger
        fileList={fileList}
        accept='.xlsx, .xls'
        headers={{'Authorization': `Bearer ${getAccessToken()}`}}
        action="/zxHealth/t/shh/jytj/uploadFile"
        beforeUpload={beforeUpload}
        onChange={handleUploadChange}
      >
        <p className="ant-upload-drag-icon">
          <InboxOutlined />
        </p>
        <p className="ant-upload-text">点击上传</p>
        <p className="ant-upload-hint">
          注意：请确认数据的完整性与准确性
        </p>
      </Dragger>
    </Modal>
  )
}

export default ModalImport;
