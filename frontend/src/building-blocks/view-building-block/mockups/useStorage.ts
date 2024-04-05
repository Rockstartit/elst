import {fileApi} from "src/services";

export function useStorage() {

  function downloadMockupImage(mockupId: string) {
    return fileApi.downloadMockup(mockupId, {
      responseType: 'blob'
    }).then(response => {
      return new Promise<string>((resolve, reject) => {
        const reader = new window.FileReader();
        reader.readAsDataURL(response.data as Blob);

        reader.onload = function() {
          if (typeof reader.result === 'string') {
            resolve(reader.result);
          } else {
            reject();
          }
        }
      })
    })
  }

  return {downloadMockupImage};
}
