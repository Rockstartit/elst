import { fileApi } from 'src/services/content_upload';

const fileNameRegex = /name="([a-zA-Z0-9.äüöÄÜÖ]+)"/;

export function useContentDownload() {
  function downloadFile(fileId: string) {
    fileApi.downloadFile(fileId, { responseType: 'blob' }).then((response) => {
      const blob = response.data as unknown as Blob;

      const url = (window.URL || window.webkitURL).createObjectURL(blob);

      let fileName = 'download';

      const fileNameMatch = fileNameRegex.exec(
        response.headers['content-disposition']
      );

      console.log(fileNameMatch);

      if (fileNameMatch && fileNameMatch.length >= 1) {
        fileName = fileNameMatch[1];
      }

      const a = document.createElement('a');
      a.href = url;
      a.download = fileName;
      document.body.appendChild(a);
      a.click();
      document.body.removeChild(a);
    });
  }

  return {
    downloadFile,
  };
}
