import { DefaultFooter } from '@ant-design/pro-layout';
import React from "react";
import { GithubOutlined } from '@ant-design/icons';
import moment from 'moment';

const GlobalFooter = () => {
  return (
    <DefaultFooter
      copyright={`${moment().format("YYYY")  } `}
      links={[]}
    />
  );
}

export default GlobalFooter;
