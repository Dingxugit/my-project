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
  const [year,handleYear] = useState(moment(new Date()).format("YYYY") );
  const [form] = Form.useForm();

  const handleExportBz = async () =>{
    fetch('/zxHealth/t/lqbz/file/exportEjYlzb', { // downloadFiles 接口请求地址
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${getAccessToken()}`,// 设置header 获取token
      })
      ,body:JSON.stringify({"year":year,})
    }).then((response) => {
      response.blob().then(blob => {
        const blobUrl = window.URL.createObjectURL(blob);
        const aElement = document.getElementById('downloadzbzDiv'); // 获取a标签元素
        // eslint-disable-next-line no-undef
        const filename = `${dept.name}油料指标供应账.xls`;// 设置文件名称
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
    handleYear(fieldsValue.year.format("YYYY")) ;
    handleExportBz();
    onCancel() ;
  }

  const changeDate = (e) =>{
      handleYear(moment(e).format("YYYY")) ;
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
          year: moment(new Date(),"YYYY")
        }}
        form={form}
    >

      <FormItem
        {...formItemLayout}
        label="年度"
        name="year"
        rule={[{required: true,message:"请填写年度"}]}
      >
        <DatePicker picker="year" onChange={changeDate}/>
      </FormItem>

    </Form>

  </Modal>
  )
}

export default ExportModal ;
