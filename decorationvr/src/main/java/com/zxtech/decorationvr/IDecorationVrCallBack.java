package com.zxtech.decorationvr;

/**
 * Created by syp466 on 2016/11/11.
 */

public interface IDecorationVrCallBack {
    void CompleteCallBack(int wpara, int lpara, String content);

    //************************************
    // Method:    DownloadData
    // FullName:  decoration_vr_interface::IDecorationVrCallBack::DownloadData
    // Access:    virtual public
    // Returns:   char*
    // Qualifier: char* 数据缓冲区的指针，缓冲区内保存的是一个json字符串，使用后需要调用FreeBuf释放，如果出错，返回nullptr
    // Parameter: const char * download_url
    // Parameter: const char * post_data
    //************************************
    String DownloadData(String download_url, String post_data);

    //************************************
    // Method:    DownloadFile
    // FullName:  decoration_vr_interface::IDecorationVrCallBack::DownloadFile
    // Access:    virtual public
    // Returns:   bool 下载成功返回true，失败返回false
    // Qualifier: 下载文件
    // Parameter: const char * download_url 下载文件的URL
    // Parameter: const char * file_abs_path 文件在本地保存的绝对路径
    //************************************
    boolean DownloadFile(String download_url, String file_abs_path);
}
