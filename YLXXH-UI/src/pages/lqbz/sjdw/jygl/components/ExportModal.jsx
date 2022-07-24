import React ,{useState} from 'react';
import {Form, Modal,DatePicker} from 'antd';
import {getAccessToken} from "@/utils/token";
import moment from 'moment' ;

const FormItem = Form.Item;

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};

const ExportModal =(props) =>{

  const { exportModalVisible, onCancel  } = props;
  const dept = JSON.parse(sessionStorage.getItem('DEPT')) ;
  const [tianbaoshijian,handleTianbaosj] = useState(moment(new Date()).format("YYYY-MM-DD") ) ;
  const [form] = Form.useForm();


  const handleExportBz = async () =>{
    fetch('/zxHealth/t/lqbz/file/exportCzpz', { // downloadFiles 接口请求地址
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${getAccessToken()}`,// 设置header 获取token
      })
      ,body:JSON.stringify({ "yongyoudanwei":dept.name,"chongzhisj":tianbaoshijian})
    }).then((response) => {
      response.blob().then(blob => {
        const blobUrl = window.URL.createObjectURL(blob);
        const aElement = document.getElementById('downloadzbzDiv'); // 获取a标签元素
        // eslint-disable-next-line no-undef
        const filename = `${dept.name}充值凭证.xls`;// 设置文件名称
        aElement.href = blobUrl;// 设置a标签路径
        aElement.download = filename;
        aElement.click();
        window.URL.revokeObjectURL(blobUrl);
      });
    }).catch((error) => {
      console.log('文件下载失败', error);
    });
  }

  const okHandle = async () => {
    const fieldsValue = await form.validateFields();
    handleTianbaosj(fieldsValue.chongzhisj.format("YYYY-MM-DD")) ;
    handleExportBz();
    onCancel() ;
  }

  const changeDate =(e) =>{
    handleTianbaosj(moment(e).format("YYYY-MM-DD")) ;
  }

  // eslint-disable-next-line no-unused-expressions
  return (<Modal
    destroyOnClose
    visible={exportModalVisible}
    onOk={okHandle}
    onCancel={onCancel}
  >
    <Form
      initialValues={{
        chongzhisj: moment(new Date())}}
      form={form}
    >

      <FormItem
        {...formItemLayout}
        label="充值时间"
        name="chongzhisj"
        rules={[{ required: true, message: '请填写充值时间!' }]}

      >
        <DatePicker style={{width: '80%'}} onChange={changeDate}/>
      </FormItem>

    </Form>

  </Modal>
  )
}

export default ExportModal ;
