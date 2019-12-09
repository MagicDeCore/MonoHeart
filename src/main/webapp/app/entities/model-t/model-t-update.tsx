import React from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IPartT } from 'app/shared/model/part-t.model';
import { getEntities as getPartTs } from 'app/entities/part-t/part-t.reducer';
import { ITypeT } from 'app/shared/model/type-t.model';
import { getEntities as getTypeTs } from 'app/entities/type-t/type-t.reducer';
import { getEntity, updateEntity, createEntity, reset } from './model-t.reducer';
import { IModelT } from 'app/shared/model/model-t.model';
import { convertDateTimeFromServer, convertDateTimeToServer } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IModelTUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export interface IModelTUpdateState {
  isNew: boolean;
  idspartT: any[];
  typeTId: string;
}

export class ModelTUpdate extends React.Component<IModelTUpdateProps, IModelTUpdateState> {
  constructor(props) {
    super(props);
    this.state = {
      idspartT: [],
      typeTId: '0',
      isNew: !this.props.match.params || !this.props.match.params.id
    };
  }

  componentWillUpdate(nextProps, nextState) {
    if (nextProps.updateSuccess !== this.props.updateSuccess && nextProps.updateSuccess) {
      this.handleClose();
    }
  }

  componentDidMount() {
    if (this.state.isNew) {
      this.props.reset();
    } else {
      this.props.getEntity(this.props.match.params.id);
    }

    this.props.getPartTs();
    this.props.getTypeTs();
  }

  saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const { modelTEntity } = this.props;
      const entity = {
        ...modelTEntity,
        ...values,
        partTS: mapIdList(values.partTS)
      };

      if (this.state.isNew) {
        this.props.createEntity(entity);
      } else {
        this.props.updateEntity(entity);
      }
    }
  };

  handleClose = () => {
    this.props.history.push('/model-t');
  };

  render() {
    const { modelTEntity, partTS, typeTS, loading, updating } = this.props;
    const { isNew } = this.state;

    return (
      <div>
        <Row className="justify-content-center">
          <Col md="8">
            <h2 id="monoHeartApp.modelT.home.createOrEditLabel">Create or edit a ModelT</h2>
          </Col>
        </Row>
        <Row className="justify-content-center">
          <Col md="8">
            {loading ? (
              <p>Loading...</p>
            ) : (
              <AvForm model={isNew ? {} : modelTEntity} onSubmit={this.saveEntity}>
                {!isNew ? (
                  <AvGroup>
                    <Label for="model-t-id">ID</Label>
                    <AvInput id="model-t-id" type="text" className="form-control" name="id" required readOnly />
                  </AvGroup>
                ) : null}
                <AvGroup>
                  <Label id="nameLabel" for="model-t-name">
                    Name
                  </Label>
                  <AvField id="model-t-name" type="text" name="name" />
                </AvGroup>
                <AvGroup>
                  <Label id="wrongLabel" for="model-t-wrong">
                    Wrong
                  </Label>
                  <AvField id="model-t-wrong" type="text" name="wrong" />
                </AvGroup>
                <AvGroup>
                  <Label for="model-t-partT">Part T</Label>
                  <AvInput
                    id="model-t-partT"
                    type="select"
                    multiple
                    className="form-control"
                    name="partTS"
                    value={modelTEntity.partTS && modelTEntity.partTS.map(e => e.id)}
                  >
                    <option value="" key="0" />
                    {partTS
                      ? partTS.map(otherEntity => (
                          <option value={otherEntity.id} key={otherEntity.id}>
                            {otherEntity.name}
                          </option>
                        ))
                      : null}
                  </AvInput>
                </AvGroup>
                <Button tag={Link} id="cancel-save" to="/model-t" replace color="info">
                  <FontAwesomeIcon icon="arrow-left" />
                  &nbsp;
                  <span className="d-none d-md-inline">Back</span>
                </Button>
                &nbsp;
                <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                  <FontAwesomeIcon icon="save" />
                  &nbsp; Save
                </Button>
              </AvForm>
            )}
          </Col>
        </Row>
      </div>
    );
  }
}

const mapStateToProps = (storeState: IRootState) => ({
  partTS: storeState.partT.entities,
  typeTS: storeState.typeT.entities,
  modelTEntity: storeState.modelT.entity,
  loading: storeState.modelT.loading,
  updating: storeState.modelT.updating,
  updateSuccess: storeState.modelT.updateSuccess
});

const mapDispatchToProps = {
  getPartTs,
  getTypeTs,
  getEntity,
  updateEntity,
  createEntity,
  reset
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(
  mapStateToProps,
  mapDispatchToProps
)(ModelTUpdate);
