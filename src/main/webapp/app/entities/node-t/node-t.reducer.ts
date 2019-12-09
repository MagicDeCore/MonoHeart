import axios from 'axios';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction, ICrudDeleteAction } from 'react-jhipster';

import { cleanEntity } from 'app/shared/util/entity-utils';
import { REQUEST, SUCCESS, FAILURE } from 'app/shared/reducers/action-type.util';

import { INodeT, defaultValue } from 'app/shared/model/node-t.model';

export const ACTION_TYPES = {
  FETCH_NODET_LIST: 'nodeT/FETCH_NODET_LIST',
  FETCH_NODET: 'nodeT/FETCH_NODET',
  CREATE_NODET: 'nodeT/CREATE_NODET',
  UPDATE_NODET: 'nodeT/UPDATE_NODET',
  DELETE_NODET: 'nodeT/DELETE_NODET',
  RESET: 'nodeT/RESET'
};

const initialState = {
  loading: false,
  errorMessage: null,
  entities: [] as ReadonlyArray<INodeT>,
  entity: defaultValue,
  updating: false,
  totalItems: 0,
  updateSuccess: false
};

export type NodeTState = Readonly<typeof initialState>;

// Reducer

export default (state: NodeTState = initialState, action): NodeTState => {
  switch (action.type) {
    case REQUEST(ACTION_TYPES.FETCH_NODET_LIST):
    case REQUEST(ACTION_TYPES.FETCH_NODET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        loading: true
      };
    case REQUEST(ACTION_TYPES.CREATE_NODET):
    case REQUEST(ACTION_TYPES.UPDATE_NODET):
    case REQUEST(ACTION_TYPES.DELETE_NODET):
      return {
        ...state,
        errorMessage: null,
        updateSuccess: false,
        updating: true
      };
    case FAILURE(ACTION_TYPES.FETCH_NODET_LIST):
    case FAILURE(ACTION_TYPES.FETCH_NODET):
    case FAILURE(ACTION_TYPES.CREATE_NODET):
    case FAILURE(ACTION_TYPES.UPDATE_NODET):
    case FAILURE(ACTION_TYPES.DELETE_NODET):
      return {
        ...state,
        loading: false,
        updating: false,
        updateSuccess: false,
        errorMessage: action.payload
      };
    case SUCCESS(ACTION_TYPES.FETCH_NODET_LIST):
      return {
        ...state,
        loading: false,
        entities: action.payload.data,
        totalItems: parseInt(action.payload.headers['x-total-count'], 10)
      };
    case SUCCESS(ACTION_TYPES.FETCH_NODET):
      return {
        ...state,
        loading: false,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.CREATE_NODET):
    case SUCCESS(ACTION_TYPES.UPDATE_NODET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: action.payload.data
      };
    case SUCCESS(ACTION_TYPES.DELETE_NODET):
      return {
        ...state,
        updating: false,
        updateSuccess: true,
        entity: {}
      };
    case ACTION_TYPES.RESET:
      return {
        ...initialState
      };
    default:
      return state;
  }
};

const apiUrl = 'api/node-ts';

// Actions

export const getEntities: ICrudGetAllAction<INodeT> = (page, size, sort) => {
  const requestUrl = `${apiUrl}${sort ? `?page=${page}&size=${size}&sort=${sort}` : ''}`;
  return {
    type: ACTION_TYPES.FETCH_NODET_LIST,
    payload: axios.get<INodeT>(`${requestUrl}${sort ? '&' : '?'}cacheBuster=${new Date().getTime()}`)
  };
};

export const getEntity: ICrudGetAction<INodeT> = id => {
  const requestUrl = `${apiUrl}/${id}`;
  return {
    type: ACTION_TYPES.FETCH_NODET,
    payload: axios.get<INodeT>(requestUrl)
  };
};

export const createEntity: ICrudPutAction<INodeT> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.CREATE_NODET,
    payload: axios.post(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const updateEntity: ICrudPutAction<INodeT> = entity => async dispatch => {
  const result = await dispatch({
    type: ACTION_TYPES.UPDATE_NODET,
    payload: axios.put(apiUrl, cleanEntity(entity))
  });
  dispatch(getEntities());
  return result;
};

export const deleteEntity: ICrudDeleteAction<INodeT> = id => async dispatch => {
  const requestUrl = `${apiUrl}/${id}`;
  const result = await dispatch({
    type: ACTION_TYPES.DELETE_NODET,
    payload: axios.delete(requestUrl)
  });
  dispatch(getEntities());
  return result;
};

export const reset = () => ({
  type: ACTION_TYPES.RESET
});
