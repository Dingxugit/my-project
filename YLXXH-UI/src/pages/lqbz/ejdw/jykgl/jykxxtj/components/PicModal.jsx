import React ,{useEffect,useState} from 'react' ;
import {Modal} from 'antd' ;

const PicModal = (props) =>{
  const {picModalVisible,carNum,onCancel} = props ;

  return (
    <Modal
      destroyOnClose
      visible={picModalVisible}
      width={1300}
      onCancel={onCancel}
    >
      <div>
        <img id="preview" src={`/assets/lqbz/${carNum}.jpg`} />
      </div>
    </Modal>
  )
}

export default PicModal ;
