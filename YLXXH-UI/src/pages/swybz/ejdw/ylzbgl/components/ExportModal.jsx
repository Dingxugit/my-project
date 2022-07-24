import React ,{useState} from 'react';
import {Form, Modal,DatePicker} from 'antd';
import {getAccessToken} from "@/utils/token";
import moment from 'moment' ;
import {DeptInfo} from "@/components/gy";

const FormItem = Form.Item;

const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 19 },
};

const ExportModal =(props) =>{

  const { exportModalVisible, onCancel  } = props;
  const dept = JSON.parse(sessionStorage.getItem('DEPT')) ;
  const [deptname,handleDeptname] = useState('' ) ;
  const [form] = Form.useForm();


  const handleExportBz = async () =>{
    fetch('/zxHealth/t/swy/file/exportSwygl', { // downloadFiles 接口请求地址
      method: 'POST',
      headers: new Headers({
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${getAccessToken()}`,// 设置header 获取token
      })
      ,body:JSON.stringify({ "yongyoudanwei":deptname})
    }).then((response) => {
      response.blob().then(blob => {
        const blobUrl = window.URL.createObjectURL(blob);
        const aElement = document.getElementById('downloadzbzDiv'); // 获取a标签元素
        // eslint-disable-next-line no-undef
        const filename = `${deptname}价拨实物油料账.xls`;// 设置文件名称
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
    handleExportBz();
    onCancel() ;
  }


  const handleDept = (e) =>{
    handleDeptname(e) ;
  }

  // eslint-disable-next-line no-unused-expressions
  return (<Modal
    destroyOnClose
    visible={exportModalVisible}
    onOk={okHandle}
    onCancel={onCancel}
  >
    <Form
      form={form}
    >

      <FormItem
        {...formItemLayout}
        label="单位"
        name="yongyoudanwei"
      >
        <DeptInfo onChange={handleDept}/>
      </FormItem>

    </Form>

  </Modal>
  )
}

export default ExportModal ;
